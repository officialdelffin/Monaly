package com.example.monaly.ui.fragment.navegation


// Importações :
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monaly.R
import com.example.monaly.ui.adapter.AlbumDetailsAdapter


// Fragmento responsável por exibir todos os detalhes e mídias de um álbum específico :
class AlbumDetailsFragment : Fragment(R.layout.fragment_album_details) {


    // Pegando o adaptador :
    private lateinit var adapter: AlbumDetailsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        val rvAlbumMedia = view.findViewById<RecyclerView>(R.id.rvAlbumMedia)


        // Iniciando o adaptador com uma lista vazia por enquanto :
        adapter = AlbumDetailsAdapter(emptyList())


        // Configurando a grade para ter exatas 3 colunas :
        val layoutManager = GridLayoutManager(requireContext(), 3)


        // Aplicando a inteligência da quebra de linha :
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {


            override fun getSpanSize(position: Int): Int {


                // Se for um cabeçalho de data, ocupe 3 colunas (a linha inteira). Se for foto, ocupe apenas 1 coluna :
                return when (adapter.getItemViewType(position)) {


                    AlbumDetailsAdapter.TYPE_HEADER -> 3
                    AlbumDetailsAdapter.TYPE_MEDIA -> 1
                    else -> 1


                }


            }


        }


        // Conectando a configuração e o adaptador ao XML da tela :
        rvAlbumMedia.layoutManager = layoutManager
        rvAlbumMedia.adapter = adapter


    }


}