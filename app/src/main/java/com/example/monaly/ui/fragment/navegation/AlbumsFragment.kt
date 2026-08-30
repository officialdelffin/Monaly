package com.example.monaly.ui.fragment.navegation


// Importações necessárias :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.monaly.R
import com.example.monaly.ui.adapter.AlbumMockModel
import com.example.monaly.ui.adapter.AlbumsAdapter
import java.util.Collections.emptyList


// Fragmento que exibe a tela principal de Álbuns :
class AlbumsFragment : Fragment() {


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        // Inflando o layout principal da tela :
        val view = inflater.inflate(R.layout.fragment_albums, container, false)


        // Instanciando uma lista completamente vazia para representar o estado inicial :
        val emptyAlbumList = emptyList<AlbumMockModel>()


        // Conectando o Adaptador à RecyclerView usando a lista vazia :
        val rvAlbums = view.findViewById<RecyclerView>(R.id.rvAlbums)
        rvAlbums.adapter = AlbumsAdapter(emptyAlbumList)


        // Localizando o texto de estado vazio recém-criado na interface XML :
        val tvEmptyState = view.findViewById<TextView>(R.id.tvEmptyState)


        // Lógica visual para exibir o texto central quando não houver dados :
        if (emptyAlbumList.isEmpty()) {


            // Escondendo a lista para não ocupar espaço invisível :
            rvAlbums.visibility = View.GONE


            // Exibindo o componente de texto no centro da tela :
            tvEmptyState.visibility = View.VISIBLE


        } else {


            // Garantindo que a lista apareça caso haja itens :
            rvAlbums.visibility = View.VISIBLE


            // Ocultando a mensagem de estado vazio :
            tvEmptyState.visibility = View.GONE


        }


        return view


    }


}