package com.example.monaly.domain.model

// Importações
import java.util.Date


// Representação estrutural de um álbum. Essa classe prepara o terreno para receber dados reais do Banco de Dados :
data class AlbumModel(


    // Textos preenchidos nos campos do formulário :
    val title: String = "",
    val description: String,


    // ID único do documento gerado automaticamente pelo Firebase :
    val id: String = "",


    // Temporário: Usaremos as imagens locais (R.drawable) até a nuvem ser conectada :
    val imageResId: Int,


    // ID do usuário criador do álbum :
    val ownerId: String = "",


    // Link da imagem de capa que será gerado pelo Storage após o upload :
    val coverUrl: String = "",


    // Chaves booleanas configuradas pelos Switches da tela (Verdadeiro/Falso) :
    val isPublic: Boolean = false,
    val allowDownload: Boolean = false,


    // Data exata em que o álbum foi criado para ordenação do Feed :
    val createdAt: Date = Date()



)