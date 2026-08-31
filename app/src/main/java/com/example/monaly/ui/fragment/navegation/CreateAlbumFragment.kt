package com.example.monaly.ui.fragment.navegation


// Importações :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.monaly.R
import com.google.android.material.button.MaterialButton


// Fragmento responsável pela tela de criação e configuração de novos álbuns :
class CreateAlbumFragment : Fragment() {


    // Criando variáveis globais temporárias para manipular a interface dentro do Lançador :
    private lateinit var ivCoverBackground: ImageView
    private lateinit var tvCoverPlaceholder: TextView
    private lateinit var btnAddCover: MaterialButton


    // Configurando o Lançador moderno do Android para selecionar fotos de forma segura sem pedir permissões :
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->


        // Se o usuário selecionou uma imagem o URI não será nulo :
        if (uri != null) {


            // Aplicando a foto escolhida no fundo escuro do card :
            ivCoverBackground.setImageURI(uri)


            // Escondendo o texto e o botão de "Adicionar capa" para limpar o visual :
            tvCoverPlaceholder.visibility = View.GONE
            btnAddCover.visibility = View.GONE


        }


    }


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        // Inflando o layout da tela de criação :
        val view = inflater.inflate(R.layout.fragment_create_album, container, false)


        // Conectando as variáveis Kotlin com os IDs criados no arquivo XML :
        ivCoverBackground = view.findViewById(R.id.imageGradientEffect)
        tvCoverPlaceholder = view.findViewById(R.id.CoverAlbum)
        btnAddCover = view.findViewById(R.id.buttonAddNewAlbum)


        // Atribuindo a ação de clique no botão para disparar a abertura da galeria de fotos :
        btnAddCover.setOnClickListener {


            // Solicitando ao sistema que abra a janela filtrando apenas por imagens :
            val request = androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            pickMedia.launch(request)


        }


        // Funcionalidade de clique no botão voltar do próprio celular para esconder a tela :
        val topArea = view.findViewById<View>(R.id.CoverAlbum)
        topArea.setOnClickListener {


            // Opcional para facilitar os testes : clique no topo para fechar a tela :
            parentFragmentManager.popBackStack()


        }


        return view


    }


}