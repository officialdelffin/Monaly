package com.example.monaly.ui.fragment.navegation


// Importações :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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


        // Inflando o layout principal :
        val view = inflater.inflate(R.layout.fragment_albums, container, false)


        // Instanciando uma lista completamente vazia para representar o estado inicial do banco de dados :
        val emptyAlbumList = emptyList<AlbumMockModel>()


        // Conectando o Adaptador à RecyclerView usando a lista vazia :
        val rvAlbums = view.findViewById<RecyclerView>(R.id.rvAlbums)
        rvAlbums.adapter = AlbumsAdapter(emptyAlbumList)


        // Lógica de Empty State protegendo a experiência do usuário :
        if (emptyAlbumList.isEmpty()) {


            // Esconde a lista para evitar espaços em branco :
            rvAlbums.visibility = View.GONE

            // Exibindo um Toast provisório com a nova String centralizada para validar o estado vazio :
            Toast.makeText(requireContext(), getString(R.string.albums_empty_state_message), Toast.LENGTH_LONG).show()


        } else {


            // Garante que a lista apareça caso haja itens :
            rvAlbums.visibility = View.VISIBLE


        }


        return view


    }


}