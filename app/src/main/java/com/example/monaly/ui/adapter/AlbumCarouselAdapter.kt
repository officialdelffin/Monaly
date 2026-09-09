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


// Adaptador inteligente que recicla a memória das visualizações do carrossel e carrega imagens da nuvem :
class AlbumCarouselAdapter(private var albums: List<AlbumModel>) : RecyclerView.Adapter<AlbumCarouselAdapter.AlbumViewHolder>() {


    // Classe interna que segura as referências dos componentes visuais do XML :
    class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val imageCover: ImageView = view.findViewById(R.id.imageAlbumCover)
        val textTitle: TextView = view.findViewById(R.id.textAlbumTitle)
        val textDescription: TextView = view.findViewById(R.id.textAlbumDescription)
        val textTag: TextView = view.findViewById(R.id.textAlbumTag)
        val textStatusPrivatePublic: TextView = view.findViewById(R.id.textStatusPrivatePublic)


    }


    // Infla o XML que criamos para cada item da lista :
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {


        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album_carousel, parent, false)
        return AlbumViewHolder(view)


    }


    // Informa ao Android quantos álbuns existem na lista :
    override fun getItemCount(): Int = albums.size


    // Injeta os dados da nuvem diretamente nos textos e usa o Glide para a imagem :
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {


        val album = albums[position]


        // Extraindo o contexto da tela para acessar o arquivo de strings e o Glide :
        val context = holder.itemView.context


        // Preenchendo os textos principais :
        holder.textTitle.text = album.title
        holder.textDescription.text = album.description


        // Utilizando o contexto para buscar os textos padrões no arquivo strings.xml :
        holder.textTag.text = context.getString(R.string.create_album_status_solo_shared)


        // Lógica para transformar o estado booleano em texto visual puxando recursos do sistema :
        holder.textStatusPrivatePublic.text = if (album.isPublic) {


            "Público"


        } else {


            context.getString(R.string.create_album_status_private_public)


        }


        // Carregando a imagem da URL diretamente do Firebase Storage via Glide :
        Glide.with(context)
            .load(album.coverUrl)
            .centerCrop()
            .into(holder.imageCover)


    }


    // Função responsável por receber dados atualizados em tempo real e repintar o carrossel :
    fun updateAlbums(newAlbums: List<AlbumModel>) {


        albums = newAlbums
        notifyDataSetChanged()


    }


}