package com.example.monaly.ui.fragment.navegation


// Importações :
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.monaly.R
import com.example.monaly.domain.model.AlbumModel
import com.example.monaly.ui.adapter.AlbumCarouselAdapter
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// Fragmento do Feed principal preparado com ciclo de vida seguro e seletor múltiplo :
class FeedFragment : Fragment(R.layout.fragment_feed) {


    // Registrando o contrato para permitir a seleção de múltiplas mídias com limite de segurança de 20 arquivos :
    private val pickMultipleMedia = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(20)) { uris: List<Uri> ->


        // Verificando se a lista retornada contém arquivos ou se o usuário fechou a galeria vazia :
        if (uris.isNotEmpty()) {


            // Contabilizando e registrando o sucesso da seleção múltipla :
            val totalSelected = uris.size
            Log.d("PhotoPicker", "Total de mídias selecionadas: $totalSelected")
            Toast.makeText(requireContext(), "$totalSelected mídias selecionadas prontas para upload!", Toast.LENGTH_SHORT).show()


            // O próximo passo será enviar esta lista 'uris' para a ViewModel processar o upload no álbum temporário :


        } else {


            // Ação caso o usuário cancele a seleção :
            Log.d("PhotoPicker", "Nenhuma mídia selecionada")


        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Encontrando os componentes de interface no layout :
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerFeedAlbums)
        val buttonAdd = view.findViewById<MaterialButton>(R.id.buttonAdd)


        // Configurando a ação de clique do botão de adicionar mídia :
        buttonAdd.setOnClickListener {


            // Lançando o seletor múltiplo nativo filtrado para exibir apenas imagens e vídeos :
            pickMultipleMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))


        }


        // Criação de Dados Simulados Mock para testar o carrossel visualmente :
        val fakeAlbums = listOf(


            AlbumModel("Acampamento em Santa Penha", "O registro da nossa aventura...", "Álbum - Público", R.color.gray_dark),
            AlbumModel("Aniversário de 25 anos", "Festa surpresa organizada...", "Álbum - Privado", R.color.gray_dark),
            AlbumModel("Viagem para a Praia", "Lembranças inesquecíveis...", "Álbum - Público", R.color.gray_dark)


        )


        // Conectando o Adaptador com os dados ao ViewPager2 :
        val adapter = AlbumCarouselAdapter(fakeAlbums)
        viewPager.adapter = adapter


        // Iniciando o motor seguro de auto-rolagem :
        startAutoScroll(viewPager, fakeAlbums.size)


    }


    // Função privada que executa o loop infinito de troca de tela de forma segura :
    private fun startAutoScroll(viewPager: ViewPager2, totalItems: Int) {


        // Garantindo que o loop seja destruído assim que a tela não estiver visível :
        viewLifecycleOwner.lifecycleScope.launch {


            while (true) {


                // Pausa de 5 segundos antes de trocar para o próximo card :
                delay(5000)


                // Descobrindo a posição atual e calculando o próximo índice :
                val currentItem = viewPager.currentItem
                val nextItem = if (currentItem < totalItems - 1) currentItem + 1 else 0


                // Alterando a visualização com uma animação de transição suave :
                viewPager.setCurrentItem(nextItem, true)


            }


        }


    }


}