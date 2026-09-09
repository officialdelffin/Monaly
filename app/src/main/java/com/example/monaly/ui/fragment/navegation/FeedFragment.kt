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
import com.example.monaly.ui.adapter.AlbumCarouselAdapter
import com.example.monaly.ui.viewmodel.AlbumsViewModel
import com.example.monaly.ui.viewmodel.UploadState
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// Fragmento do Feed conectado à ViewModel de upload e álbuns :
class FeedFragment : Fragment(R.layout.fragment_feed) {


    // Instanciando as ViewModels de forma tardia (lazy) :
    private lateinit var feedViewModel: FeedViewModel
    private lateinit var albumsViewModel: AlbumsViewModel


    // Declarando o adaptador do carrossel para atualização futura :
    private lateinit var carouselAdapter: AlbumCarouselAdapter


    // Controle para evitar múltiplas chamadas de rolagem automática :
    private var isAutoScrollStarted = false


    // Registrando o contrato para seleção múltipla :
    private val pickMultipleMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uris: List<Uri> ->


        if (uris.isNotEmpty()) {


            // Disparando o upload em lote diretamente para a ViewModel :
            feedViewModel.uploadMediaBatch(uris)


        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Conectando as ViewModels ao ciclo de vida do Fragmento :
        feedViewModel = ViewModelProvider(this)[FeedViewModel::class.java]
        albumsViewModel = ViewModelProvider(this)[AlbumsViewModel::class.java]


        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerFeedAlbums)
        val buttonAdd = view.findViewById<MaterialButton>(R.id.buttonAdd)


        // Inicializando o carrossel com uma lista vazia :
        carouselAdapter = AlbumCarouselAdapter(emptyList())
        viewPager.adapter = carouselAdapter


        buttonAdd.setOnClickListener {


            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))


        }


        // Observando os estados de upload da FeedViewModel :
        viewLifecycleOwner.lifecycleScope.launch {


            feedViewModel.uploadState.collect { state ->


                when (state) {


                    // Estado neutro aguardando ação :
                    is UploadState.Idle -> {}


                    is UploadState.Uploading -> {


                        // Exibindo o progresso do lote para o usuário :
                        Toast.makeText(requireContext(), "Enviando ${state.current} de ${state.total}...", Toast.LENGTH_SHORT).show()


                    }


                    is UploadState.Success -> {


                        // Avisando a conclusão e limpando a área :
                        Toast.makeText(requireContext(), "Upload concluído com sucesso!", Toast.LENGTH_LONG).show()


                    }


                    is UploadState.Error -> {


                        // Exibindo a falha amigavelmente :
                        Toast.makeText(requireContext(), "Erro: ${state.message}", Toast.LENGTH_LONG).show()


                    }


                    else -> {}


                }


            }


        }


        // Observando os álbuns reais vindos da AlbumsViewModel :
        albumsViewModel.albumsList.observe(viewLifecycleOwner) { albums ->


            if (albums.isEmpty()) {


                // Ocultando o carrossel se não houver álbuns para exibir :
                viewPager.visibility = View.GONE


            } else {


                // Exibindo o carrossel e atualizando os dados do adaptador :
                viewPager.visibility = View.VISIBLE
                carouselAdapter.updateAlbums(albums)


                // Iniciando a rolagem automática de forma segura (apenas uma vez) :
                if (!isAutoScrollStarted) {


                    isAutoScrollStarted = true
                    startAutoScroll(viewPager)


                }


            }


        }


    }


    private fun startAutoScroll(viewPager: ViewPager2) {


        viewLifecycleOwner.lifecycleScope.launch {


            while (true) {


                delay(5000)


                // Obtendo a quantidade atualizada de itens no carrossel :
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