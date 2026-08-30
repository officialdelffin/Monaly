package com.example.monaly.ui.adapter


// Importações :
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.monaly.R


// Criando um modelo simples de dados para representar um álbum :
data class AlbumMockModel(val title: String, val status: String, val description: String, val colorHex: String)


// Adaptador responsável por ligar a lista de dados ao layout do RecyclerView :
class AlbumsAdapter(private val albums: List<AlbumMockModel>) : RecyclerView.Adapter<AlbumsAdapter.AlbumViewHolder>() {


    // Classe interna que segura as referências do layout do card para melhorar a performance :
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


    // Verificando a quantidade total de itens que a lista precisará desenhar :
    override fun getItemCount(): Int {


        return albums.size


    }


    // Preenchendo os dados do modelo no ViewHolder correto de acordo com a posição da rolagem :
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {


        val album = albums[position]


        // Injetando os textos nos componentes visuais :
        holder.tvTitle.text = album.title
        holder.tvStatus.text = album.status
        holder.tvDescription.text = album.description


        // Aplicando uma cor sólida simulando uma imagem de capa diferente para cada card :
        holder.ivCover.setBackgroundColor(Color.parseColor(album.colorHex))


    }


}