package com.example.monaly.ui.fragment.navegation


// Importações necessárias :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.monaly.R
import com.example.monaly.ui.adapter.AlbumsAdapter
import com.example.monaly.ui.viewmodel.AlbumsViewModel


// Fragmento que exibe a tela principal de Álbuns :
class AlbumsFragment : Fragment() {


    // Declarando o gerenciador que trará os dados da nuvem :
    private lateinit var viewModel: AlbumsViewModel


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        // Inflando o layout principal da tela :
        val view = inflater.inflate(R.layout.fragment_albums, container, false)


        // Instanciando o ViewModel respeitando o ciclo de vida da tela atual :
        viewModel = ViewModelProvider(this).get(AlbumsViewModel::class.java)


        // Conectando os componentes visuais com os IDs do arquivo XML :
        val rvAlbums = view.findViewById<RecyclerView>(R.id.rvAlbums)
        val tvEmptyState = view.findViewById<TextView>(R.id.tvEmptyState)
        val btnAddAlbum = view.findViewById<View>(R.id.buttonAddNewAlbum)


        // Observando a lista de álbuns vinda do banco de dados em tempo real :
        viewModel.albumsList.observe(viewLifecycleOwner) { albums ->


            // Atualizando o adaptador da RecyclerView com os dados reais baixados da nuvem :
            rvAlbums.adapter = AlbumsAdapter(albums)


            // Lógica visual dinâmica para exibir a lista ou o estado de tela vazia :
            if (albums.isEmpty()) {


                // Escondendo a lista e mostrando o texto central caso não existam álbuns :
                rvAlbums.visibility = View.GONE
                tvEmptyState.visibility = View.VISIBLE


            }

            else {


                // Exibindo a lista e ocultando a mensagem caso existam álbuns baixados :
                rvAlbums.visibility = View.VISIBLE
                tvEmptyState.visibility = View.GONE


            }


        }


        // Configurando a ação de clique para abrir a nova tela de criação :
        btnAddAlbum.setOnClickListener {


            // Utilizando o gerente interno para abrir a tela sobrepondo o conteúdo atual :
            childFragmentManager.beginTransaction()


                .add(R.id.albumsRootContainer, CreateAlbumFragment())
                .addToBackStack(null)
                .commit()


        }


        // O retorno da view finaliza o desenho da tela :
        return view


    }


}