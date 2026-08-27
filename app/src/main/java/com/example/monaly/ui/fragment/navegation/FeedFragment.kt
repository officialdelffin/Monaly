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
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// Fragmento do Feed conectado à ViewModel de upload :
class FeedFragment : Fragment(R.layout.fragment_feed) {


    // Instanciando a ViewModel de forma tardia (lazy) :
    private lateinit var viewModel: FeedViewModel


    // Registrando o contrato para seleção múltipla :
    private val pickMultipleMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(10)) { uris: List<Uri> ->


        if (uris.isNotEmpty()) {


            // Disparando o upload em lote diretamente para a ViewModel :
            viewModel.uploadMediaBatch(uris)


        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Conectando a ViewModel ao ciclo de vida do Fragmento :
        viewModel = ViewModelProvider(this)[FeedViewModel::class.java]


        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerFeedAlbums)
        val buttonAdd = view.findViewById<MaterialButton>(R.id.buttonAdd)


        buttonAdd.setOnClickListener {


            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))


        }


        // Observando os estados da ViewModel para atualizar a interface em tempo real :
        viewLifecycleOwner.lifecycleScope.launch {


            viewModel.uploadState.collect { state ->


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


                }


            }


        }


        // Criação de Dados Mock :
        val fakeAlbums = listOf(


            AlbumModel("Acampamento em Santa Penha", "O registro da nossa aventura...", "Álbum - Público", R.color.gray_dark),
            AlbumModel("Aniversário de 25 anos", "Festa surpresa organizada...", "Álbum - Privado", R.color.gray_dark),
            AlbumModel("Viagem para a Praia", "Lembranças inesquecíveis...", "Álbum - Público", R.color.gray_dark)


        )


        val adapter = AlbumCarouselAdapter(fakeAlbums)
        viewPager.adapter = adapter
        startAutoScroll(viewPager, fakeAlbums.size)


    }


    private fun startAutoScroll(viewPager: ViewPager2, totalItems: Int) {


        viewLifecycleOwner.lifecycleScope.launch {


            while (true) {


                delay(5000)
                val currentItem = viewPager.currentItem
                val nextItem = if (currentItem < totalItems - 1) currentItem + 1 else 0
                viewPager.setCurrentItem(nextItem, true)


            }


        }


    }


}