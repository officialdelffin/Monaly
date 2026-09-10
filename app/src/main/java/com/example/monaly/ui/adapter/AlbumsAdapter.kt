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


// Adaptador responsável por ligar a lista de dados reais ao layout do RecyclerView :
class AlbumsAdapter(private val albums: List<AlbumModel>) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {


    // Classe interna mapeando todos os textos do XML para garantir controle visual total :
    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val ivCover: ImageView = view.findViewById(R.id.ivAlbumCover)
        val tvStatus: TextView = view.findViewById(R.id.tvAlbumStatus)
        val tvTitle: TextView = view.findViewById(R.id.tvAlbumTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvAlbumDescription)
        val textSpaceItemAlbum: TextView = view.findViewById(R.id.textSpaceItemAlbum)
        val textStatusItemAlbum: TextView = view.findViewById(R.id.textStatusItemAlbum)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)


    }


    override fun getItemCount(): Int {


        return albums.size


    }


    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {


        val album = albums[position]
        val context = holder.itemView.context


        holder.tvTitle.text = album.title
        holder.tvDescription.text = album.description


        val privacyText = if (album.isPublic) "Público" else "Privado"
        holder.tvStatus.text = "Álbum solo - $privacyText"


        // Deixando todos os campos de texto 100% transparentes antes de solicitar a imagem :
        holder.tvTitle.alpha = 0f
        holder.tvDescription.alpha = 0f
        holder.tvStatus.alpha = 0f
        holder.textSpaceItemAlbum.alpha = 0f
        holder.textStatusItemAlbum.alpha = 0f


        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.setColorSchemeColors(Color.WHITE)
        circularProgressDrawable.start()


        // Solicitando a imagem e implementando o ouvinte com as variáveis corretas do AlbumsAdapter :
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
                    holder.tvTitle.alpha = 1f
                    holder.tvDescription.alpha = 1f
                    holder.tvStatus.alpha = 1f
                    holder.textSpaceItemAlbum.alpha = 1f
                    holder.textStatusItemAlbum.alpha = 1f
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
                    holder.tvTitle.animate().alpha(1f).setDuration(duration).start()
                    holder.tvDescription.animate().alpha(1f).setDuration(duration).start()
                    holder.tvStatus.animate().alpha(1f).setDuration(duration).start()
                    holder.textSpaceItemAlbum.animate().alpha(1f).setDuration(duration).start()
                    holder.textStatusItemAlbum.animate().alpha(1f).setDuration(duration).start()
                    return false


                }


            })


            .into(holder.ivCover)


    }


}