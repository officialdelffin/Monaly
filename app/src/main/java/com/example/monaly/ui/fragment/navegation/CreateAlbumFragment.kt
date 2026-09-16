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
import androidx.lifecycle.ViewModelProvider
import com.example.monaly.R
import com.example.monaly.domain.model.AlbumModel
import com.example.monaly.ui.viewmodel.CreateAlbumViewModel
import com.example.monaly.ui.viewmodel.UploadState
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import android.content.Context
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.widget.doOnTextChanged


// Fragmento responsável pela tela de criação e configuração de novos álbuns :
class CreateAlbumFragment : Fragment() {


    // Criando variáveis globais para manipular a interface e extrair os dados :
    private lateinit var ivCoverBackground: ImageView
    private lateinit var btnAddCover: MaterialButton
    private lateinit var btnSaveAlbum: MaterialButton
    private lateinit var btnBack : ImageView
    private lateinit var etTitle: TextInputEditText
    private lateinit var etDescription: TextInputEditText
    private lateinit var switchPublic: SwitchMaterial
    private lateinit var switchDownload: SwitchMaterial
    private lateinit var viewModel: CreateAlbumViewModel
    private lateinit var tvPreviewTitle : TextView
    private lateinit var tvPreviewDescription : TextView
    private lateinit var tvPreviewStatus : TextView
    private lateinit var tvPreviewPrivacy : TextView


    // Variável para armazenar temporariamente o endereço (URI) da imagem escolhida :
    private var selectedCoverUri: Uri? = null


    // Configurando o Lançador moderno do Android para selecionar fotos :
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->


        if (uri != null) {


            // Armazenando o link e atualizando a interface :
            selectedCoverUri = uri
            ivCoverBackground.setImageURI(uri)
            btnAddCover.text = getString(R.string.create_album_button_change_cover)
            btnAddCover.setIconResource(android.R.drawable.ic_menu_edit)


        }


    }


    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {


        val view = inflater.inflate(R.layout.fragment_create_album, container, false)


        // Instanciando o ViewModel de forma segura respeitando o ciclo de vida da tela :
        viewModel = ViewModelProvider(this).get(CreateAlbumViewModel::class.java)


        // Conectando as variáveis Kotlin com os IDs do arquivo XML :
        ivCoverBackground = view.findViewById(R.id.ivCoverPhoto)
        btnAddCover = view.findViewById(R.id.buttonAddNewAlbum)
        btnSaveAlbum = view.findViewById(R.id.buttomSaveAlbum)
        btnBack = view.findViewById<ImageView>(R.id.btnBack)
        etTitle = view.findViewById(R.id.EditTitleField)
        etDescription = view.findViewById(R.id.EditDescriptionField)
        switchPublic = view.findViewById(R.id.switchPublic)
        switchDownload = view.findViewById(R.id.switchDownload)
        tvPreviewTitle = view.findViewById<TextView>(R.id.textView6)
        tvPreviewDescription = view.findViewById<TextView>(R.id.textView3)
        tvPreviewStatus = view.findViewById<TextView>(R.id.textView7)
        tvPreviewPrivacy = view.findViewById<TextView>(R.id.textView9)


        // Atualizando o título da capa em tempo real durante a digitação :
        etTitle.doOnTextChanged { text, _, _, _ ->


            // Verificando se o texto está vazio para manter o valor padrão ou atualizar com o texto digitado :
            if (text.isNullOrEmpty()) {


                tvPreviewTitle.text = getString(R.string.create_album_title_field)


            }


            else {


                tvPreviewTitle.text = text.toString()


            }


        }


        // Atualizando a descrição da capa em tempo real durante a digitação :
        etDescription.doOnTextChanged { text, _, _, _ ->


            // Mantendo a descrição genérica caso o usuário apague tudo :
            if (text.isNullOrEmpty()) {


                tvPreviewDescription.text = getString(R.string.create_album_description_field)


            }


            else {


                tvPreviewDescription.text = text.toString()


            }


        }


        // Forçando o Kotlin a quebrar a linha visualmente e impedindo a rolagem para o lado :
        etTitle.setHorizontallyScrolling(false)
        etTitle.maxLines = 2


        etDescription.setHorizontallyScrolling(false)
        etDescription.maxLines = 10


        // Interceptando a tecla Concluir do teclado virtual e também a tecla Enter física :
        etDescription.setOnEditorActionListener { _, actionId, event ->


            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.keyCode == android.view.KeyEvent.KEYCODE_ENTER && event.action == android.view.KeyEvent.ACTION_DOWN)) {


                // Removendo o cursor piscante e o foco do campo de texto :
                etDescription.clearFocus()


                // Solicitando ao sistema operacional que esconda o teclado virtual da tela :
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(etDescription.windowToken, 0)


                // Retornando verdadeiro para avisar que o clique foi interceptado e processado :
                true


            } else {


                // Retornando falso caso outra tecla tenha sido pressionada :
                false


            }


        }


        // Escutando a mudança da chave de público/privado para atualizar o texto correspondente :
        switchPublic.setOnCheckedChangeListener { _, isChecked ->


            // Atualizando a palavra exibida na capa com base no estado do interruptor :
            if (isChecked) {


                tvPreviewPrivacy.text = "Público"


            }


            else {


                tvPreviewPrivacy.text = "Privado"


            }


        }


        // Lógica preparada e isolada para o futuro sistema de participantes :
        val mockParticipantsCount = 0


        if (mockParticipantsCount > 0) {


            // Alternando para álbum compartilhado caso existam convidados na lista :
            tvPreviewStatus.text = "Álbum compartilhado"


        }


        else {


            // Mantendo o status padrão para álbuns sem convidados :
            tvPreviewStatus.text = "Álbum solo"


        }


        // Observando as respostas da nuvem emitidas pelo ViewModel em tempo real :
        viewModel.uploadState.observe(viewLifecycleOwner) { state ->


            when (state) {


                is UploadState.Idle -> {


                    // Estado livre que não exige alteração visual nesta tela :
                }


                is UploadState.Loading -> {


                    // Bloqueando os botões e alterando o texto para indicar o processamento :
                    btnSaveAlbum.isEnabled = false
                    btnSaveAlbum.text = "Salvando..."
                    btnAddCover.isEnabled = false
                    etTitle.isEnabled = false
                    etDescription.isEnabled = false


                }


                is UploadState.Uploading -> {


                    // Estado não utilizado pela criação de álbum :
                }


                is UploadState.Success -> {


                    // Avisando o sucesso e fechando a tela de criação automaticamente :
                    Toast.makeText(requireContext(), "Álbum criado com sucesso!", Toast.LENGTH_LONG).show()
                    parentFragmentManager.popBackStack()


                }


                is UploadState.Error -> {


                    // Restaurando os botões caso a internet falhe ou ocorra um erro :
                    btnSaveAlbum.isEnabled = true
                    btnSaveAlbum.text = getString(R.string.create_album_button_save)
                    btnAddCover.isEnabled = true
                    etTitle.isEnabled = true
                    etDescription.isEnabled = true

                    // Exibindo o motivo do erro para o usuário :
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()


                }


            }


        }


        // Abrir galeria ao clicar no botão da capa :
        btnAddCover.setOnClickListener {


            val request = androidx.activity.result.PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            pickMedia.launch(request)


        }


        // Disparar o salvamento e a validação :
        btnSaveAlbum.setOnClickListener {


            val titleText = etTitle.text.toString().trim()
            val descriptionText = etDescription.text.toString().trim()


            // Validação de segurança :
            if (selectedCoverUri == null) {


                Toast.makeText(requireContext(), "Por favor, adicione uma capa ao álbum.", Toast.LENGTH_SHORT).show()


            } else if (titleText.isEmpty()) {


                Toast.makeText(requireContext(), "Por favor, insira um título para o álbum.", Toast.LENGTH_SHORT).show()


            } else if (descriptionText.isEmpty()) {


                Toast.makeText(requireContext(), "Por favor, insira uma descrição.", Toast.LENGTH_SHORT).show()


            } else {


                // Tudo validado! Criando o pacote do álbum e pedindo ao ViewModel para subir para a nuvem :
                val newAlbum = AlbumModel(


                    title = titleText,
                    description = descriptionText,
                    isPublic = switchPublic.isChecked,
                    allowDownload = switchDownload.isChecked


                )

                // Entregando os dados brutos para o cérebro processar :
                viewModel.createAlbum(newAlbum, selectedCoverUri!!)


            }


        }


        // Funcionalidade de clique explícito na seta para voltar e fechar a tela :
        btnBack.setOnClickListener {


            // Removendo o fragmento atual e retornando para a lista de álbuns :
            parentFragmentManager.popBackStack()


        }


        return view


    }


}