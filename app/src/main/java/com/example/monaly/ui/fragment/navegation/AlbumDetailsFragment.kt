package com.example.monaly.ui.fragment.navegation


// Importações :
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monaly.R
import com.example.monaly.domain.model.AlbumDetailItem
import com.example.monaly.domain.model.MediaModel
import com.example.monaly.ui.adapter.AlbumDetailsAdapter
import java.util.Date


// Fragmento responsável por exibir todos os detalhes e mídias de um álbum específico :
class AlbumDetailsFragment : Fragment(R.layout.fragment_album_details) {


    // Declarando o adaptador em escopo global para permitir atualizações futuras :
    private lateinit var adapter: AlbumDetailsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        val rvAlbumMedia = view.findViewById<RecyclerView>(R.id.rvAlbumMedia)


        // Iniciando o adaptador passando a nossa lista de dados falsos (Mocks) para teste visual :
        adapter = AlbumDetailsAdapter(generateMockData())


        // Configurando a grade para ter exatas 3 colunas :
        val layoutManager = GridLayoutManager(requireContext(), 3)


        // Aplicando a inteligência da quebra de linha para o cabeçalho de data :
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {


            override fun getSpanSize(position: Int): Int {


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


    // Função privada para gerar dados fictícios e testar o comportamento visual da lista :
    private fun generateMockData(): List<AlbumDetailItem> {


        val mockList = mutableListOf<AlbumDetailItem>()


        // Criando o primeiro cabeçalho de data :
        mockList.add(AlbumDetailItem.DateHeader("Atualizado em 14 de Setembro de 2026"))


        // Injetando 4 fotos de teste (Natureza) vinculadas à data acima :
        for (i in 1..4) {


            val fakeMedia = MediaModel(mediaUrl = "https://images.unsplash.com/photo-1506744626753-140130541708?q=80&w=500&auto=format&fit=crop")
            mockList.add(AlbumDetailItem.Media(fakeMedia))


        }


        // Criando o segundo cabeçalho de data simulando uma atualização mais antiga :
        mockList.add(AlbumDetailItem.DateHeader("Atualizado em 10 de Setembro de 2026"))


        // Injetando 7 fotos de teste (Arquitetura) vinculadas à segunda data :
        for (i in 1..7) {


            val fakeMedia = MediaModel(mediaUrl = "https://images.unsplash.com/photo-1472214103451-9374bd1c798e?q=80&w=500&auto=format&fit=crop")
            mockList.add(AlbumDetailItem.Media(fakeMedia))


        }


        return mockList


    }


}