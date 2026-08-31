package com.example.monaly.domain.model


// Importações :
import java.util.Date


// Classe de dados que espelha exatamente os campos que serão salvos no Firestore :
data class AlbumModel(


    // ID único do documento gerado automaticamente pelo Firebase :
    val id: String = "",


    // ID do usuário criador do álbum :
    val ownerId: String = "",


    // Textos preenchidos nos campos do formulário :
    val title: String = "",
    val description: String = "",


    // Link da imagem de capa que será gerado pelo Storage após o upload :
    val coverUrl: String = "",


    // Chaves booleanas configuradas pelos Switches da tela (Verdadeiro/Falso) :
    val isPublic: Boolean = false,
    val allowDownload: Boolean = false,


    // Data exata em que o álbum foi criado para ordenação do Feed :
    val createdAt: Date = Date()


)