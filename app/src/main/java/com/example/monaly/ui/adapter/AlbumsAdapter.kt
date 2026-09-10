package com.example.monaly.ui.adapter


// Importações :
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.monaly.R
import com.example.monaly.domain.model.AlbumModel
import android.graphics.Color


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


        // Extraindo o contexto para a criação da animação :
        val context = holder.itemView.context


        // Injetando os textos nos componentes visuais :
        holder.tvTitle.text = album.title
        holder.tvDescription.text = album.description


        // Lógica para transformar o estado booleano em texto visual :
        val privacyText = if (album.isPublic) "Público" else "Privado"
        holder.tvStatus.text = "Álbum solo - $privacyText"


        // Criando a animação circular nativa do Android para servir de espaço reservado :
        val circularProgressDrawable = CircularProgressDrawable(context)


        // Ajustando a espessura do traço e o tamanho do círculo :
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f

        // Pintando a animação de branco para garantir um alto contraste com o fundo escuro :
        circularProgressDrawable.setColorSchemeColors(Color.WHITE)


        // Iniciando o giro infinito da animação :
        circularProgressDrawable.start()


        // Utilizando o Glide para carregar a imagem exibindo a animação circular até finalizar :
        Glide.with(context)
            .load(album.coverUrl)
            .placeholder(circularProgressDrawable)
            .centerCrop()
            .into(holder.ivCover)


    }


}