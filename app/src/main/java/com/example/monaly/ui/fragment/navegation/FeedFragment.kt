package com.example.monaly.ui.fragment.navegation


// Importações :
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.monaly.R
import com.example.monaly.domain.model.AlbumModel
import com.example.monaly.ui.adapter.AlbumCarouselAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// Fragmento do Feed principal preparado com ciclo de vida seguro :
class FeedFragment : Fragment(R.layout.fragment_feed) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Encontra o ViewPager2 do carrossel no layout :
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerFeedAlbums)


        // Criação de Dados Simulados Mock para testarmos o design até a Nuvem chegar e a substitua 'R.color.gray_dark' por imagens reais suas (ex: R.drawable.sua_foto) para ver o resultado prático :
        val fakeAlbums = listOf(


            AlbumModel("Acampamento em Santa Penha", "O registro da nossa aventura. Foram dias de pé no chão...", "Álbum compartilhado - Público", R.color.gray_dark),
            AlbumModel("Aniversário de 25 anos", "Festa surpresa organizada pela família com muita alegria.", "Álbum - Privado", R.color.gray_dark),
            AlbumModel("Viagem para a Praia", "Lembranças inesquecíveis do litoral durante o verão passado.", "Álbum compartilhado - Público", R.color.gray_dark)


        )


        // Conecta o Adaptador com os dados ao ViewPager2 :
        val adapter = AlbumCarouselAdapter(fakeAlbums)
        viewPager.adapter = adapter


        // Inicia o motor seguro de auto-rolagem a cada 5 segundos :
        startAutoScroll(viewPager, fakeAlbums.size)


    }


    // Função privada que executa o loop infinito de troca de tela de forma segura usando Coroutines :
    private fun startAutoScroll(viewPager: ViewPager2, totalItems: Int) {


        // viewLifecycleOwner garante que o loop seja destruído assim que o usuário sair do Feed :
        viewLifecycleOwner.lifecycleScope.launch {


            while (true) {


                // Aguarda 5000 milissegundos sendo 5 segundos :
                delay(5000)


                // Descobre a posição atual :
                val currentItem = viewPager.currentItem


                // Calcula a próxima página. Se for a última, volta para a posição 0 :
                val nextItem = if (currentItem < totalItems - 1) currentItem + 1 else 0


                // Muda a tela com animação suave :
                viewPager.setCurrentItem(nextItem, true)


            }


        }


    }


}