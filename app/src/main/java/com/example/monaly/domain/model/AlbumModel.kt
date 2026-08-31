package com.example.monaly.domain.model

// Importações
import java.util.Date


// Representação estrutural de um álbum. Essa classe prepara o terreno para receber dados reais do Banco de Dados :
data class AlbumModel(


    // Organizado por ordem :

    // Textos preenchidos nos campos do formulário
    // Textos preenchidos nos campos do formulário
    // ID único do documento gerado automaticamente pelo Firebase
    // Temporário: Usaremos as imagens locais (R.drawable) até a nuvem ser conectada
    // ID do usuário criador do álbum
    // Link da imagem de capa que será gerado pelo Storage após o upload
    // Chaves booleanas configuradas pelos Switches da tela Verdadeiro/Falso
    // Chaves booleanas configuradas pelos Switches da tela Verdadeiro/Falso
    // Data exata em que o álbum foi criado para ordenação do Feed


    val title: String = "",
    val description: String,
    val id: String = "",
    val imageResId: Int,
    val ownerId: String = "",
    val coverUrl: String = "",
    val isPublic: Boolean = false,
    val allowDownload: Boolean = false,
    val createdAt: Date = Date()


)