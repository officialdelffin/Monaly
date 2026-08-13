package com.example.monaly.ui.adapter


// Importações :
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.monaly.R
import com.example.monaly.domain.model.AlbumModel


// Adaptador inteligente que recicla a memória das visualizações do carrossel :
class AlbumCarouselAdapter(private val albums: List<AlbumModel>) : RecyclerView.Adapter<AlbumCarouselAdapter.AlbumViewHolder>() {


    // Classe interna que segura as referências dos componentes visuais do XML para não ter que procurá-los toda hora :
    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val imageCover: ImageView = view.findViewById(R.id.imageAlbumCover)
        val textTitle: TextView = view.findViewById(R.id.textAlbumTitle)
        val textDescription: TextView = view.findViewById(R.id.textAlbumDescription)
        val textTag: TextView = view.findViewById(R.id.textAlbumTag)


    }


    // Infla o XML que criamos para cada item da lista :
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album_carousel, parent, false)
        return AlbumViewHolder(view)


    }


    // Informa ao Android quantos álbuns existem na lista :
    override fun getItemCount(): Int = albums.size


    // Injeta os dados da posição atual diretamente nos textos e imagens da tela :
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {


        val album = albums[position]


        holder.textTitle.text = album.title
        holder.textDescription.text = album.description
        holder.textTag.text = album.tag
        holder.imageCover.setImageResource(album.imageResId)


    }


}