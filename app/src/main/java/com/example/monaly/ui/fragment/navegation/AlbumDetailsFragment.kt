package com.example.monaly.ui.fragment.navegation


// Importacoes necessarias :
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
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


// Fragmento responsavel por exibir os detalhes injetando apenas dados reais :
class AlbumDetailsFragment : Fragment(R.layout.fragment_album_details) {


    private lateinit var adapter: AlbumDetailsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Mapeando todos os componentes, incluindo os tres campos da linha de status :
        val tvTitle = view.findViewById<TextView>(R.id.tvDetailTitle)
        val tvDescription = view.findViewById<TextView>(R.id.tvDetailDescription)
        val tvStatusType = view.findViewById<TextView>(R.id.tvDetailStatus)
        val tvStatusSeparator = view.findViewById<TextView>(R.id.tvDetailSeparator)
        val tvStatusPrivacy = view.findViewById<TextView>(R.id.tvDetailPrivacySolo)
        val ivCover = view.findViewById<ImageView>(R.id.ivDetailCover)
        val btnBack = view.findViewById<ImageView>(R.id.btnBackDetail)
        val rvAlbumMedia = view.findViewById<RecyclerView>(R.id.rvAlbumMedia)


        // Extraindo parametros repassados pelo clique nas telas anteriores :
        val albumTitle = arguments?.getString("ALBUM_TITLE") ?: ""
        val albumDesc = arguments?.getString("ALBUM_DESCRIPTION") ?: ""
        val coverUrl = arguments?.getString("ALBUM_COVER_URL") ?: ""
        val isPublic = arguments?.getBoolean("ALBUM_IS_PUBLIC") ?: false


        // Povoando os textos dinamicos principais :
        tvTitle.text = albumTitle
        tvDescription.text = albumDesc


        // Montando a frase do status de forma contínua utilizando os tres espacos :
        tvStatusType.text = "Álbum solo"
        tvStatusSeparator.text = "-"
        tvStatusPrivacy.text = if (isPublic) "Público" else "Privado"


        // Configurando botao de retrocesso para fechar a tela atual :
        btnBack.setOnClickListener {


            parentFragmentManager.popBackStack()


        }


        // Configuracao da animacao de carregamento :
        val circularProgressDrawable = CircularProgressDrawable(requireContext())
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(Color.WHITE)
        circularProgressDrawable.start()


        ivCover.alpha = 0f


        // Gerenciamento da imagem de capa via biblioteca de terceiros :
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


        // Inicializando o adaptador com uma lista totalmente vazia, eliminando dados ficticios :
        adapter = AlbumDetailsAdapter(emptyList())


        val spanCount = 5
        val layoutManager = GridLayoutManager(requireContext(), spanCount)


        // Orientando o comportamento do grid com base no tipo de conteudo retornado :
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


    }


}