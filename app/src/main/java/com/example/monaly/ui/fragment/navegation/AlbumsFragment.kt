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
import com.example.monaly.domain.model.AlbumModel
import com.example.monaly.ui.adapter.AlbumsAdapter
import com.example.monaly.ui.viewmodel.AlbumsViewModel


// Fragmento que exibe a tela principal de Álbuns :
class AlbumsFragment : Fragment() {


    private lateinit var viewModel: AlbumsViewModel
    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        val view = inflater.inflate(R.layout.fragment_albums, container, false)
        viewModel = ViewModelProvider(this).get(AlbumsViewModel::class.java)


        val rvAlbums = view.findViewById<RecyclerView>(R.id.rvAlbums)
        val tvEmptyState = view.findViewById<TextView>(R.id.tvEmptyState)
        val btnAddAlbum = view.findViewById<View>(R.id.buttonAddNewAlbum)


        viewModel.albumsList.observe(viewLifecycleOwner) { albums ->


            // Instanciando o adaptador com o ouvinte de cliques ativado :
            rvAlbums.adapter = AlbumsAdapter(albums) { clickedAlbum ->


                openAlbumDetails(clickedAlbum)


            }


            if (albums.isEmpty()) {


                rvAlbums.visibility = View.GONE
                tvEmptyState.visibility = View.VISIBLE


            } else {


                rvAlbums.visibility = View.VISIBLE
                tvEmptyState.visibility = View.GONE


            }


        }


        btnAddAlbum.setOnClickListener {


            // Aplicando as animações customizadas antes de adicionar a tela :
            childFragmentManager.beginTransaction()


                .setCustomAnimations(


                    R.anim.slide_in_right,      // Animação de entrada da nova tela :
                    R.anim.slide_out_left,       // Animação de saída da tela atual :
                    R.anim.slide_in_left,    // Animação de retorno da tela atual :
                    R.anim.slide_out_right    // Animação de saída da tela que está fechando :


                )


                .add(R.id.albumsRootContainer, CreateAlbumFragment())
                .addToBackStack(null)
                .commit()


        }


        return view


    }


    // Funcao responsavel por empacotar os dados e realizar a transicao na lista de albuns :
    private fun openAlbumDetails(album: AlbumModel) {


        val bundle = Bundle().apply {


            putString("ALBUM_ID", album.id)
            putString("ALBUM_TITLE", album.title)
            putString("ALBUM_DESCRIPTION", album.description)
            putString("ALBUM_COVER_URL", album.coverUrl)
            putBoolean("ALBUM_IS_PUBLIC", album.isPublic)


        }


        val detailsFragment = AlbumDetailsFragment().apply {


            arguments = bundle


        }


        // Utilizando o gerenciador local e aplicando as animacoes de deslizamento antes de adicionar a tela :
        childFragmentManager.beginTransaction()


            .setCustomAnimations(


                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right


            )
            .add(R.id.albumsRootContainer, detailsFragment)
            .addToBackStack(null)
            .commit()


    }


}