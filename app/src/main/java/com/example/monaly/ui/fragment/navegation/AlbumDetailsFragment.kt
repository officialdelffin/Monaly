package com.example.monaly.ui.fragment.navegation


// Importações necessárias :
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.monaly.R
import com.example.monaly.ui.adapter.AlbumDetailsAdapter
import com.example.monaly.ui.viewmodel.AlbumDetailsViewModel
import com.example.monaly.ui.viewmodel.UploadState
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch


// Fragmento responsável por exibir os detalhes e gerenciar envios injetando dados reais :
class AlbumDetailsFragment : Fragment(R.layout.fragment_album_details) {


    private lateinit var adapter: AlbumDetailsAdapter
    private lateinit var viewModel: AlbumDetailsViewModel
    private var currentAlbumId: String = ""


    // Registrando o lançador moderno para selecionar múltiplas fotos da galeria :
    private val pickMultipleMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(15)) { uris: List<Uri> ->


        if (uris.isNotEmpty() && currentAlbumId.isNotEmpty()) {


            // Disparando a função do ViewModel para iniciar o upload em lote :
            viewModel.uploadMediaToAlbum(uris, currentAlbumId)


        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Instanciando o ViewModel respeitando o ciclo de vida do fragmento :
        viewModel = ViewModelProvider(this)[AlbumDetailsViewModel::class.java]


        // Mapeando todos os componentes da tela :
        val tvTitle = view.findViewById<TextView>(R.id.tvDetailTitle)
        val tvDescription = view.findViewById<TextView>(R.id.tvDetailDescription)
        val tvStatusType = view.findViewById<TextView>(R.id.tvDetailStatus)
        val tvStatusSeparator = view.findViewById<TextView>(R.id.tvDetailSeparator)
        val tvStatusPrivacy = view.findViewById<TextView>(R.id.tvDetailPrivacySolo)
        val ivCover = view.findViewById<ImageView>(R.id.ivDetailCover)
        val btnBack = view.findViewById<ImageView>(R.id.btnBackDetail)
        val rvAlbumMedia = view.findViewById<RecyclerView>(R.id.rvAlbumMedia)
        val btnAddMedia = view.findViewById<MaterialButton>(R.id.buttonAddMedia)
        val progressUpload = view.findViewById<ProgressBar>(R.id.progressUploadMedia)
        val llEmptyState = view.findViewById<View>(R.id.llEmptyState) // Mapeando o bloco de estado vazio


        // Extraindo parâmetros repassados pelo clique nas telas anteriores :
        currentAlbumId = arguments?.getString("ALBUM_ID") ?: ""
        val albumTitle = arguments?.getString("ALBUM_TITLE") ?: ""
        val albumDesc = arguments?.getString("ALBUM_DESCRIPTION") ?: ""
        val coverUrl = arguments?.getString("ALBUM_COVER_URL") ?: ""
        val isPublic = arguments?.getBoolean("ALBUM_IS_PUBLIC") ?: false


        // Solicitando ao ViewModel que busque as fotos no banco de dados assim que a tela possuir o ID do álbum :
        viewModel.loadMedia(currentAlbumId)


        // Povoando os textos dinâmicos principais :
        tvTitle.text = albumTitle
        tvDescription.text = albumDesc


        // Montando a frase do status de forma contínua utilizando os três espaços :
        tvStatusType.text = "Álbum solo"
        tvStatusSeparator.text = " - "
        tvStatusPrivacy.text = if (isPublic) "Público" else "Privado"


        // Configurando botão de retrocesso para fechar a tela atual :
        btnBack.setOnClickListener {


            parentFragmentManager.popBackStack()


        }


        // Configuração da animação de carregamento do cabeçalho :
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(Color.WHITE)
        circularProgressDrawable.start()


        ivCover.alpha = 0f


        // Gerenciamento da imagem de capa via biblioteca Glide :
        Glide.with(this)
            .load(coverUrl)
            .placeholder(circularProgressDrawable)
            .centerCrop()
            .listener(object : RequestListener<Drawable> {


                override fun onLoadFailed(


                    p0: GlideException?,
                    p1: Any?,
                    p2: Target<Drawable?>,
                    p3: Boolean


                ): Boolean {


                    ivCover.alpha = 1f
                    return false


                }


                override fun onResourceReady(


                    p0: Drawable,
                    p1: Any,
                    p2: Target<Drawable?>?,
                    p3: DataSource,
                    p4: Boolean


                ): Boolean {


                    ivCover.animate().alpha(1f).setDuration(400L).start()
                    return false


                }


            })
            .into(ivCover)


        // Inicializando o adaptador da grade de fotos com uma lista totalmente vazia :
        adapter = AlbumDetailsAdapter(emptyList())


        val spanCount = 4
        val layoutManager = GridLayoutManager(requireContext(), spanCount)


        // Orientando o comportamento da grade no gerenciador de layout :
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {


            override fun getSpanSize(position: Int): Int {


                return when (adapter.getItemViewType(position)) {


                    AlbumDetailsAdapter.TYPE_HEADER -> spanCount
                    AlbumDetailsAdapter.TYPE_MEDIA -> 1
                    else -> 1


                }


            }


        }


        rvAlbumMedia.layoutManager = layoutManager
        rvAlbumMedia.adapter = adapter


        // Abrindo o seletor visual ao clicar em adicionar mídia :
        btnAddMedia.setOnClickListener {


            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))


        }


        // Observando os estados emitidos pelo ViewModel para atualizar a interface de carregamento :
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.uploadState.collect { state ->


                when (state) {


                    is UploadState.Idle -> {


                        // Estado inicial livre para interação :
                        btnAddMedia.visibility = View.VISIBLE
                        progressUpload.visibility = View.GONE
                        btnBack.isEnabled = true


                    }


                    is UploadState.Loading -> {}


                    is UploadState.Uploading -> {


                        // Ocultando botão e exibindo indicador giratório enquanto envia para a nuvem :
                        btnAddMedia.visibility = View.INVISIBLE
                        progressUpload.visibility = View.VISIBLE
                        btnBack.isEnabled = false


                    }


                    is UploadState.Success -> {


                        // Restaurando interface após sucesso do upload :
                        btnAddMedia.visibility = View.VISIBLE
                        progressUpload.visibility = View.GONE
                        btnBack.isEnabled = true
                        Toast.makeText(requireContext(), "Mídias salvas com sucesso!", Toast.LENGTH_SHORT).show()

                        // Retornando ao estado inicial para permitir novos envios imediatamente :
                        viewModel.resetState()


                    }


                    is UploadState.Error -> {


                        // Restaurando interface em caso de falha de conexão :
                        btnAddMedia.visibility = View.VISIBLE
                        progressUpload.visibility = View.GONE
                        btnBack.isEnabled = true
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()


                    }


                }


            }


        }


        // Observador isolado para escutar a lista de mídias e alternar a visibilidade da tela :
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.mediaList.collect { items ->


                if (items.isEmpty()) {

                    // Se a lista estiver vazia, esconde a grade e mostra a mensagem de boas-vindas :
                    rvAlbumMedia.visibility = View.GONE
                    llEmptyState.visibility = View.VISIBLE

                } else {

                    // Se houver itens, esconde a mensagem, exibe a grade e injeta os dados :
                    llEmptyState.visibility = View.GONE
                    rvAlbumMedia.visibility = View.VISIBLE
                    adapter.updateItems(items)

                }


            }


        }


    }


}