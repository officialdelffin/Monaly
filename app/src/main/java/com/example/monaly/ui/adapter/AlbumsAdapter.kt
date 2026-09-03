package com.example.monaly.ui.adapter


// Importações :
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.monaly.R
import com.example.monaly.domain.model.AlbumModel


// Adaptador responsável por ligar a lista de dados reais ao layout do RecyclerView :
class AlbumsAdapter(private val albums: List<AlbumModel>) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {


    // Classe interna que segura as referências do layout do card :
    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val ivCover: ImageView = view.findViewById(R.id.ivAlbumCover)
        val tvStatus: TextView = view.findViewById(R.id.tvAlbumStatus)
        val tvTitle: TextView = view.findViewById(R.id.tvAlbumTitle)
        val tvDescription: TextView = view.findViewById(R.id.tvAlbumDescription)


    }


    // Inflando o arquivo XML item_album para transformá-lo em uma View visual :
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)


    }


    override fun getItemCount(): Int {


        return albums.size


    }


    // Preenchendo os dados do modelo no ViewHolder correto :
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {


        val album = albums[position]


        // Injetando os textos nos componentes visuais :
        holder.tvTitle.text = album.title
        holder.tvDescription.text = album.description


        // Lógica para transformar o estado booleano em texto visual :
        val privacyText = if (album.isPublic) "Público" else "Privado"
        holder.tvStatus.text = "Álbum solo - $privacyText"


        // Utilizando o Glide para carregar a imagem da URL diretamente para o ImageView de forma assíncrona :
        Glide.with(holder.itemView.context)


            .load(album.coverUrl)
            .centerCrop()
            .into(holder.ivCover)


    }


}