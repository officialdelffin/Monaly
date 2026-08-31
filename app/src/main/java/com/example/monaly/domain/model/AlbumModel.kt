package com.example.monaly.domain.model

// Importações
import java.util.Date


// Representação estrutural de um álbum. Essa classe prepara o terreno para receber dados reais do Banco de Dados :
data class AlbumModel(


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