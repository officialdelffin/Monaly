package com.example.monaly.ui.fragment.navegation


// Importações necessárias :
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
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
import androidx.viewpager2.widget.ViewPager2
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
import kotlin.math.abs


// Fragmento encarregado da interface de detalhes e gestão de mídias :
class AlbumDetailsFragment : Fragment(R.layout.fragment_album_details) {


    private lateinit var adapter: AlbumDetailsAdapter
    private lateinit var viewModel: AlbumDetailsViewModel
    private var currentAlbumId: String = ""


    // Lançador configurado para seleção de galeria local :
    private val pickMultipleMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(15)) { uris: List<Uri> ->


        if (uris.isNotEmpty() && currentAlbumId.isNotEmpty()) {


            // Chamada de carregamento para a nuvem :
            viewModel.uploadMediaToAlbum(uris, currentAlbumId)


        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Instanciação segura do modelo de visão :
        viewModel = ViewModelProvider(this)[AlbumDetailsViewModel::class.java]


        // Mapeamento dos componentes de interface :
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
        val llEmptyState = view.findViewById<View>(R.id.llEmptyState)


        // Recebimento de parâmetros externos :
        currentAlbumId = arguments?.getString("ALBUM_ID") ?: ""
        val albumTitle = arguments?.getString("ALBUM_TITLE") ?: ""
        val albumDesc = arguments?.getString("ALBUM_DESCRIPTION") ?: ""
        val coverUrl = arguments?.getString("ALBUM_COVER_URL") ?: ""
        val isPublic = arguments?.getBoolean("ALBUM_IS_PUBLIC") ?: false


        // Inicialização da busca de dados :
        viewModel.loadMedia(currentAlbumId)


        // Preenchimento textual da interface :
        tvTitle.text = albumTitle
        tvDescription.text = albumDesc
        tvStatusType.text = "Álbum solo"
        tvStatusSeparator.text = " - "
        tvStatusPrivacy.text = if (isPublic) "Público" else "Privado"


        // Configuração de retrocesso :
        btnBack.setOnClickListener {


            parentFragmentManager.popBackStack()


        }


        // Construção do indicador de progresso dinâmico :
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(Color.WHITE)
        circularProgressDrawable.start()


        ivCover.alpha = 0f


        // Gerenciamento gráfico da capa via Glide :
        Glide.with(this)
            .load(coverUrl)
            .placeholder(circularProgressDrawable)
            .centerCrop()
            .listener(object : RequestListener<Drawable> {


                override fun onLoadFailed(
                    p0: GlideException?, p1: Any?, p2: Target<Drawable?>, p3: Boolean
                ): Boolean {


                    ivCover.alpha = 1f
                    return false


                }


                override fun onResourceReady(
                    p0: Drawable, p1: Any, p2: Target<Drawable?>?, p3: DataSource, p4: Boolean
                ): Boolean {


                    ivCover.animate().alpha(1f).setDuration(400L).start()
                    return false


                }


            })
            .into(ivCover)


        // Inicializando adaptador escutando índice e lista de URLs :
        adapter = AlbumDetailsAdapter(emptyList()) { initialPosition, urls ->


            // Acionando a janela sobreposta com carrossel dinâmico :
            showImageViewerDialog(initialPosition, urls)


        }


        // Configuração estrutural da grade :
        val spanCount = 4
        val layoutManager = GridLayoutManager(requireContext(), spanCount)


        // Distribuição de pesos de coluna :
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


        // Configuração do botão central de envio :
        btnAddMedia.setOnClickListener {


            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))


        }


        // Escuta reativa de status de processamento :
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.uploadState.collect { state ->


                when (state) {


                    is UploadState.Idle -> {


                        btnAddMedia.visibility = View.VISIBLE
                        progressUpload.visibility = View.GONE
                        btnBack.isEnabled = true


                    }


                    is UploadState.Loading -> {}


                    is UploadState.Uploading -> {


                        btnAddMedia.visibility = View.INVISIBLE
                        progressUpload.visibility = View.VISIBLE
                        btnBack.isEnabled = false


                    }


                    is UploadState.Success -> {


                        btnAddMedia.visibility = View.VISIBLE
                        progressUpload.visibility = View.GONE
                        btnBack.isEnabled = true
                        Toast.makeText(requireContext(), "Mídias salvas com sucesso!", Toast.LENGTH_SHORT).show()
                        viewModel.resetState()


                    }


                    is UploadState.Error -> {


                        btnAddMedia.visibility = View.VISIBLE
                        progressUpload.visibility = View.GONE
                        btnBack.isEnabled = true
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()


                    }


                }


            }


        }


        // Escuta reativa de dados estruturados para desenho na grade :
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.mediaList.collect { items ->


                if (items.isEmpty()) {


                    rvAlbumMedia.visibility = View.GONE
                    llEmptyState.visibility = View.VISIBLE


                } else {


                    llEmptyState.visibility = View.GONE
                    rvAlbumMedia.visibility = View.VISIBLE
                    adapter.updateItems(items)


                }


            }


        }


    }


    // Função responsável pela exibição do Dialog carrossel :
    private fun showImageViewerDialog(initialPosition: Int, urls: List<String>) {


        // Instanciamento de tema imersivo nativo :
        val dialog = android.app.Dialog(requireContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.dialog_image_viewe)


        // Mapeamento do ViewPager2 e fechamento :
        val vpViewer = dialog.findViewById<ViewPager2>(R.id.vpFullscreenMedia)
        val btnClose = dialog.findViewById<ImageView>(R.id.btnViewerClose)


        btnClose.setOnClickListener {


            dialog.dismiss()


        }


        // Conectando o adaptador interno do carrossel injetando a função de fechar janela :
        val pagerAdapter = FullscreenPagerAdapter(urls) {


            dialog.dismiss()


        }


        vpViewer.adapter = pagerAdapter


        // Posicionando o carrossel diretamente na foto clicada na grade :
        vpViewer.setCurrentItem(initialPosition, false)


        dialog.show()


    }


    // Classe interna adaptadora para processamento de visualização em tela cheia :
    private inner class FullscreenPagerAdapter(


        private val urls: List<String>,
        private val onDismiss: () -> Unit


    ) : RecyclerView.Adapter<FullscreenPagerAdapter.ImageViewHolder>() {


        // Mapeamento interno de visualizador :
        inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {


            val ivFullscreenItem: ImageView = view.findViewById(R.id.ivFullscreenItem)


        }


        // Insuflador do item singular do carrossel :
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {


            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fullscreen_image, parent, false)
            return ImageViewHolder(view)


        }


        // Contagem espelhada de URLs :
        override fun getItemCount(): Int = urls.size


        // Processamento reativo do ciclo de vida da imagem tocável :
        @SuppressLint("ClickableViewAccessibility")
        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {


            val url = urls[position]


            // Configuração do motor visual com suporte a transparência natural :
            Glide.with(holder.itemView.context)


                .load(url)
                .into(holder.ivFullscreenItem)


            // Variáveis lógicas de medição do vetor de toque :
            var startY = 0f
            var startX = 0f
            var isDragging = false


            // Injeção de detector táctil validando a física do arrasto de fechamento :
            holder.ivFullscreenItem.setOnTouchListener { view, event ->


                when (event.action) {


                    MotionEvent.ACTION_DOWN -> {


                        // Registrando as coordenadas iniciais sem bloquear o carrossel horizontal :
                        startY = event.rawY
                        startX = event.rawX
                        isDragging = false
                        false


                    }


                    MotionEvent.ACTION_MOVE -> {


                        val deltaY = event.rawY - startY
                        val deltaX = event.rawX - startX


                        // Avaliando se a força vertical supera a horizontal para acionar o modo arraste :
                        if (abs(deltaY) > abs(deltaX) && abs(deltaY) > 50) {


                            isDragging = true
                            view.translationY = deltaY
                            view.alpha = 1f - (abs(deltaY) / 1000f)


                            // Consumindo o evento para neutralizar interferência do ViewPager2 :
                            true


                        }


                        else {


                            false


                        }


                    }


                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {


                        if (isDragging) {


                            val deltaY = event.rawY - startY


                            // Concluindo ação de fechamento se a barreira limite for rompida :
                            if (abs(deltaY) > 300) {


                                onDismiss()


                            }


                            else {


                                // Restituindo propriedades físicas caso o gesto seja abortado :
                                view.animate().translationY(0f).alpha(1f).setDuration(200).start()


                            }


                            isDragging = false
                            true


                        }

                        else {


                            false


                        }


                    }


                    else -> false


                }


            }


        }


    }


}