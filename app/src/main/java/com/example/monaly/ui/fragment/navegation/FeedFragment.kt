package com.example.monaly.ui.fragment.navegation


// Importações necessárias :
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.monaly.R
import com.example.monaly.domain.model.AlbumModel
import com.example.monaly.ui.adapter.AlbumCarouselAdapter
import com.example.monaly.ui.viewmodel.AlbumsViewModel
import com.example.monaly.ui.viewmodel.UploadState
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// Fragmento do Feed conectado à ViewModel de upload e álbuns :
class FeedFragment : Fragment(R.layout.fragment_feed) {


    private lateinit var feedViewModel: FeedViewModel
    private lateinit var albumsViewModel: AlbumsViewModel
    private lateinit var carouselAdapter: AlbumCarouselAdapter
    private var isAutoScrollStarted = false


    private val pickMultipleMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uris: List<Uri> ->


        if (uris.isNotEmpty()) {


            feedViewModel.uploadMediaBatch(uris)


        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        feedViewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        albumsViewModel = ViewModelProvider(this)[AlbumsViewModel::class.java]


        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerFeedAlbums)
        val buttonAdd = view.findViewById<MaterialButton>(R.id.buttonAdd)


        // Instanciando o adaptador e implementando o comportamento de clique para abrir os detalhes :
        carouselAdapter = AlbumCarouselAdapter(emptyList()) { clickedAlbum ->


            openAlbumDetails(clickedAlbum)


        }


        viewPager.adapter = carouselAdapter


        buttonAdd.setOnClickListener {


            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))


        }


        viewLifecycleOwner.lifecycleScope.launch {


            feedViewModel.uploadState.collect { state ->


                when (state) {


                    is UploadState.Idle -> {}
                    is UploadState.Uploading -> Toast.makeText(requireContext(), "Enviando ${state.current} de ${state.total}...", Toast.LENGTH_SHORT).show()
                    is UploadState.Success -> Toast.makeText(requireContext(), "Upload concluído com sucesso!", Toast.LENGTH_LONG).show()
                    is UploadState.Error -> Toast.makeText(requireContext(), "Erro: ${state.message}", Toast.LENGTH_LONG).show()


                    else -> {}


                }


            }


        }


        albumsViewModel.albumsList.observe(viewLifecycleOwner) { albums ->


            if (albums.isEmpty()) {


                viewPager.visibility = View.GONE


            } else {


                viewPager.visibility = View.VISIBLE
                carouselAdapter.updateAlbums(albums)


                if (!isAutoScrollStarted) {


                    isAutoScrollStarted = true
                    startAutoScroll(viewPager)


                }


            }


        }


    }


    // Funcao responsavel por empacotar os dados e realizar a transicao de tela de forma segura :
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


        // Aplicando a transicao suave para sobrepor a raiz do Feed sem emendas abruptas :
        childFragmentManager.beginTransaction()


            .setCustomAnimations(


                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right


            )
            .add(R.id.feedOverlayContainer, detailsFragment)
            .addToBackStack(null)
            .commit()


    }


    private fun startAutoScroll(viewPager: ViewPager2) {


        viewLifecycleOwner.lifecycleScope.launch {


            while (true) {


                delay(5000)
                val totalItems = carouselAdapter.itemCount


                if (totalItems > 0) {


                    val currentItem = viewPager.currentItem
                    val nextItem = if (currentItem < totalItems - 1) currentItem + 1 else 0
                    viewPager.setCurrentItem(nextItem, true)


                }


            }


        }


    }


}