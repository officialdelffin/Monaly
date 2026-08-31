package com.example.monaly.ui.fragment.navegation


// Importações :
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.monaly.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText


// Fragmento responsável pela tela de criação e configuração de novos álbuns :
class CreateAlbumFragment : Fragment() {


    // Criando variáveis globais para manipular a interface e extrair os dados :
    private lateinit var ivCoverBackground: ImageView
    private lateinit var btnAddCover: MaterialButton
    private lateinit var btnSaveAlbum: MaterialButton
    private lateinit var etTitle: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var switchPublic: SwitchMaterial
    private lateinit var switchDownload: SwitchMaterial


    // Variável para armazenar temporariamente o endereço (URI) da imagem escolhida :
    private var selectedCoverUri: Uri? = null


    // Configurando o Lançador moderno do Android para selecionar fotos :
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->


        // Verificando se a seleção foi bem-sucedida :
        if (uri != null) {


            // Armazenando o link da imagem na variável global para a validação no salvamento :
            selectedCoverUri = uri


            // Aplicando a foto escolhida no fundo do card :
            ivCoverBackground.setImageURI(uri)


            // Alterando o texto e ícone do botão para indicar a possibilidade de edição :
            btnAddCover.text = getString(R.string.create_album_button_change_cover)
            btnAddCover.setIconResource(android.R.drawable.ic_menu_edit)


        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflando o layout da tela de criação :
        val view = inflater.inflate(R.layout.fragment_create_album, container, false)


        // Conectando as variáveis Kotlin com os IDs do arquivo XML atualizado :
        ivCoverBackground = view.findViewById(R.id.ivCoverPhoto)
        btnAddCover = view.findViewById(R.id.buttonAddNewAlbum)
        btnSaveAlbum = view.findViewById(R.id.btnSaveAlbum)
        etTitle = view.findViewById(R.id.EditTitleField)
        etDescription = view.findViewById(R.id.EditDescriptionField)
        switchPublic = view.findViewById(R.id.switchPublic)
        switchDownload = view.findViewById(R.id.switchDownload)


        // Atribuindo a ação de clique no botão para disparar a abertura da galeria de fotos :
        btnAddCover.setOnClickListener {


            // Solicitando ao sistema que abra a janela filtrando apenas por imagens :
            val request = androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            pickMedia.launch(request)


        }


        // Configurando a ação do botão de salvar álbum :
        btnSaveAlbum.setOnClickListener {


            // Extraindo os textos digitados e removendo espaços vazios acidentais nas pontas (trim) :
            val titleText = etTitle.text.toString().trim()
            val descriptionText = etDescription.text.toString().trim()


            // Verificando hierarquicamente se todos os campos obrigatórios foram preenchidos :
            if (selectedCoverUri == null) {


                // Disparando alerta caso a foto de capa não tenha sido escolhida :
                Toast.makeText(requireContext(), "Por favor, adicione uma capa ao álbum.", Toast.LENGTH_SHORT).show()


            } else if (titleText.isEmpty()) {


                // Disparando alerta caso o título esteja vazio :
                Toast.makeText(requireContext(), "Por favor, insira um título para o álbum.", Toast.LENGTH_SHORT).show()


            } else if (descriptionText.isEmpty()) {


                // Disparando alerta caso a descrição esteja vazia :
                Toast.makeText(requireContext(), "Por favor, insira uma descrição.", Toast.LENGTH_SHORT).show()


            } else {


                // Capturando os estados finais das configurações do álbum (booleanos) :
                val isAlbumPublic = switchPublic.isChecked
                val canDownload = switchDownload.isChecked


                // Feedback temporário confirmando que o formulário está perfeito :
                Toast.makeText(requireContext(), "Validação concluída! Pronto para subir para a nuvem.", Toast.LENGTH_LONG).show()


            }


        }


        // Funcionalidade de clique no topo para fechar a tela de forma rápida :
        val topArea = view.findViewById<View>(R.id.ivCoverPhoto)
        topArea.setOnClickListener {


            // Retornando para a tela principal de álbuns :
            parentFragmentManager.popBackStack()


        }


        // O retorno da view finaliza o desenho da tela :
        return view


    }


}