package com.example.monaly.ui.fragment.navegation


// Importações :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.monaly.R
import com.google.android.material.button.MaterialButton


// Fragmento responsável pela tela de criação e configuração de novos álbuns :
class CreateAlbumFragment : Fragment() {


    // Criando variáveis globais temporárias. O TextView foi removido pois não existe mais no XML :
    private lateinit var ivCoverBackground: ImageView
    private lateinit var btnAddCover: MaterialButton


    // Configurando o Lançador moderno do Android para selecionar fotos :
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->


        // Se o usuário selecionou uma imagem o URI não será nulo :
        if (uri != null) {


            // Aplicando a foto escolhida no fundo escuro do card :
            ivCoverBackground.setImageURI(uri)


            // Escondendo apenas o botão de "Adicionar capa" já que o texto foi removido do design :
            btnAddCover.visibility = View.GONE


        }


    }


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        // Inflando o layout da tela de criação :
        val view = inflater.inflate(R.layout.fragment_create_album, container, false)


        // Conectando as variáveis Kotlin com os IDs corretos do arquivo XML atualizado :
        ivCoverBackground = view.findViewById(R.id.imageGradientEffect)
        btnAddCover = view.findViewById(R.id.buttonAddNewAlbum)


        // Atribuindo a ação de clique no botão para disparar a abertura da galeria de fotos :
        btnAddCover.setOnClickListener {


            // Solicitando ao sistema que abra a janela filtrando apenas por imagens :
            val request = androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            pickMedia.launch(request)


        }


        // Funcionalidade de clique no topo para fechar a tela :
        val topArea = view.findViewById<View>(R.id.CoverAlbum)
        topArea.setOnClickListener {


            // Retornando para a tela anterior ao clicar na área superior :
            parentFragmentManager.popBackStack()


        }


        // O retorno da view finaliza o desenho da tela :
        return view


    }


}