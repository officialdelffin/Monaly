package com.example.monaly.ui.fragment.navegation


// Importações :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.monaly.R
import com.example.monaly.ui.adapter.AlbumMockModel
import com.example.monaly.ui.adapter.AlbumsAdapter


// Fragmento que exibe a tela principal de Álbuns :
class AlbumsFragment : Fragment() {


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        // Inflando o layout da tela que contém a RecyclerView :
        val view = inflater.inflate(R.layout.fragment_albums, container, false)


        // Criando a lista de dados falsos (mock) para testar a interface com diferentes cenários :
        val mockList = listOf(


            AlbumMockModel(
                title = "Acampamento em Santa Penha",
                status = "Álbum compartilhado - Público",
                description = "O registro da nossa aventura em Santa Penha. Foram dias incríveis.",
                colorHex = "#2E7D32"
            ),


            AlbumMockModel(
                title = "Visita aos campos de Girassóis",
                status = "Álbum compartilhado - Privado",
                description = "Sobre o dia entre os girassóis. Entre risadas e muitas tentativas de fotos.",
                colorHex = "#F57F17"
            ),


            AlbumMockModel(
                title = "Festa surpresa do João",
                status = "Álbum Privado",
                description = "Fotos da festa surpresa. Não compartilhar antes do dia 15!",
                colorHex = "#1565C0"
            )


        )


        // Localizando a RecyclerView na tela e conectando o Adaptador carregado com a lista mockada :
        val rvAlbums = view.findViewById<RecyclerView>(R.id.rvAlbums)
        rvAlbums.adapter = AlbumsAdapter(mockList)


        return view


    }


}