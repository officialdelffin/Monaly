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


    // Criando variáveis globais temporárias :
    private lateinit var ivCoverBackground: ImageView
    private lateinit var btnAddCover: MaterialButton


    // Configurando o Lançador moderno do Android para selecionar fotos :
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->


        // Se o usuário selecionou uma imagem o URI não será nulo :
        if (uri != null) {


            // Aplicando a foto escolhida no fundo do card :
            ivCoverBackground.setImageURI(uri)


            // Alterando o texto do botão para indicar a possibilidade de substituição :
            btnAddCover.text = getString(R.string.create_album_button_change_cover)


            // Trocando o ícone de '+' pelo ícone de lápis nativo do Android :
            btnAddCover.setIconResource(android.R.drawable.ic_menu_edit)


        }


    }


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        // Inflando o layout da tela de criação :
        val view = inflater.inflate(R.layout.fragment_create_album, container, false)


        // ATENÇÃO: Conectando a variável ao NOVO ID da foto (e não mais ao gradiente) :
        ivCoverBackground = view.findViewById(R.id.ivCoverPhoto)


        // Conectando o botão :
        btnAddCover = view.findViewById(R.id.buttonAddNewAlbum)


        // Atribuindo a ação de clique no botão para disparar a abertura da galeria de fotos :
        btnAddCover.setOnClickListener {


            // Solicitando ao sistema que abra a janela filtrando apenas por imagens :
            val request = androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            pickMedia.launch(request)


        }


        // Funcionalidade de clique no topo para fechar a tela :
        val topArea = view.findViewById<View>(R.id.ivCoverPhoto)
        topArea.setOnClickListener {


            // Retornando para a tela anterior ao clicar na área superior :
            parentFragmentManager.popBackStack()


        }


        // O retorno da view finaliza o desenho da tela :
        return view


    }


}