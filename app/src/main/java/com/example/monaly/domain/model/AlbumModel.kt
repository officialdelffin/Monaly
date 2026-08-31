package com.example.monaly.domain.model

// Importações
import java.util.Date


// Representação estrutural de um álbum. Essa classe prepara o terreno para receber dados reais do Banco de Dados :
data class AlbumModel(



    val title: String = "",                 // Textos preenchidos nos campos do formulário :
    val description: String,                // Textos preenchidos nos campos do formulário :
    val id: String = "",                    // ID único do documento gerado automaticamente pelo Firebase :
    val imageResId: Int,                    // Temporário: Usaremos as imagens locais (R.drawable) até a nuvem ser conectada :
    val ownerId: String = "",               // ID do usuário criador do álbum :
    val coverUrl: String = "",              // Link da imagem de capa que será gerado pelo Storage após o upload :
    val isPublic: Boolean = false,          // Chaves booleanas configuradas pelos Switches da tela (Verdadeiro/Falso) :
    val allowDownload: Boolean = false,     // Chaves booleanas configuradas pelos Switches da tela (Verdadeiro/Falso) :
    val createdAt: Date = Date()            // Data exata em que o álbum foi criado para ordenação do Feed :



)