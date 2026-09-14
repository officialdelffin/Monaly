package com.example.monaly.ui.adapter


// Importações :
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.monaly.R
import com.example.monaly.domain.model.AlbumModel


// Adaptador inteligente que recicla a memória das visualizações do carrossel e carrega imagens da nuvem :
class AlbumCarouselAdapter(private var albums: List<AlbumModel>, private val onAlbumClick: (AlbumModel) -> Unit) : RecyclerView.Adapter<AlbumCarouselAdapter.AlbumViewHolder>() {


    // Classe interna que segura as referências dos componentes visuais do XML, incluindo textos estáticos :
    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val imageCover: ImageView = view.findViewById(R.id.imageAlbumCover)
        val textTitle: TextView = view.findViewById(R.id.textAlbumTitle)
        val textDescription: TextView = view.findViewById(R.id.textAlbumDescription)
        val textTag: TextView = view.findViewById(R.id.textAlbumTag)
        val textStatusPrivatePublic: TextView = view.findViewById(R.id.textStatusPrivatePublic)
        val textSpaceDisplay: TextView = view.findViewById(R.id.textSpaceDisplay)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album_carousel, parent, false)
        return AlbumViewHolder(view)


    }


    override fun getItemCount(): Int = albums.size


    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {


        val album = albums[position]
        val context = holder.itemView.context


        holder.textTitle.text = album.title
        holder.textDescription.text = album.description
        holder.textTag.text = context.getString(R.string.create_album_status_solo_shared)


        holder.textStatusPrivatePublic.text = if (album.isPublic) {


            "Público"


        } else {


            context.getString(R.string.create_album_status_private_public)


        }


        // Ocultando todos os textos através da transparência para manter o layout intacto durante o carregamento :
        holder.textTitle.alpha = 0f

        holder.textDescription.alpha = 0f

        holder.textTag.alpha = 0f

        holder.textStatusPrivatePublic.alpha = 0f

        holder.textSpaceDisplay.alpha = 0f


        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(Color.WHITE)
        circularProgressDrawable.start()


        // Carregando a imagem e instalando o ouvinte para sincronizar a visibilidade :
        Glide.with(context)


            .load(album.coverUrl)
            .placeholder(circularProgressDrawable)
            .centerCrop()
            .listener(object : RequestListener<Drawable> {


                override fun onLoadFailed(


                    p0: GlideException?,
                    p1: Any?,
                    p2: Target<Drawable?>,
                    p3: Boolean


                ): Boolean {


                    // Restaurando a opacidade dos textos em caso de falha na rede :
                    holder.textTitle.alpha = 1f
                    holder.textDescription.alpha = 1f
                    holder.textTag.alpha = 1f
                    holder.textStatusPrivatePublic.alpha = 1f
                    holder.textSpaceDisplay.alpha = 1f
                    return false


                }


                override fun onResourceReady(


                    p0: Drawable,
                    p1: Any,
                    p2: Target<Drawable?>?,
                    p3: DataSource,
                    p4: Boolean


                ): Boolean {


                    // Aninhando a animação de Fade In utilizando as variáveis reais do card de álbuns :
                    val duration = 400L
                    holder.textTitle.animate().alpha(1f).setDuration(duration).start()
                    holder.textDescription.animate().alpha(1f).setDuration(duration).start()
                    holder.textTag.animate().alpha(1f).setDuration(duration).start()
                    holder.textStatusPrivatePublic.animate().alpha(1f).setDuration(duration).start()
                    holder.textSpaceDisplay.animate().alpha(1f).setDuration(duration).start()
                    return false


                }


            })


            .into(holder.imageCover)


        // Configurando o escutador de cliques no cartão inteiro :
        holder.itemView.setOnClickListener {


            // Avisando o Fragmento qual álbum foi tocado :
            onAlbumClick(album)


        }


    }


    fun updateAlbums(newAlbums: List<AlbumModel>) {


        albums = newAlbums
        notifyDataSetChanged()


    }


}