package com.example.monaly.domain.model


// Representação estrutural de um álbum. Essa classe prepara o terreno para receber dados reais do Banco de Dados :
data class AlbumModel(


    val title: String,
    val description: String,
    val tag: String,


    // Temporário: Usaremos as imagens locais (R.drawable) até a nuvem ser conectada :
    val imageResId: Int


)