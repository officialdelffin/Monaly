package com.example.monaly.domain.model


// Importações :
import java.util.Date


// Classe de dados responsável por estruturar as informações da mídia antes de salvar no Firestore : 
data class MediaModel(


    // Identificador único do documento gerado automaticamente pelo banco de dados :
    val id: String = "",


    // Identificador do usuário dono da mídia para garantir o isolamento e a segurança :
    val userId: String = "",


    // Identificador do álbum ao qual esta mídia pertence (pode ser nulo caso não esteja em nenhum álbum) :
    val albumId: String? = null,


    // Link direto para download do arquivo bruto armazenado no Firebase Storage :
    val mediaUrl: String = "",


    // Categoria do arquivo para instruir a interface sobre como exibi-lo (exemplo: "image" ou "video") :
    val mediaType: String = "",


    // Tamanho do arquivo em bytes fundamental para o cálculo de escalabilidade e planos de assinatura :
    val sizeInBytes: Long = 0L,


    // Data exata da criação para permitir a organização cronológica perfeita na Galeria :
    val createdAt: Date = Date()


)