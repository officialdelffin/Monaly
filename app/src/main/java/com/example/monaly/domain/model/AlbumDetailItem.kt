package com.example.monaly.domain.model


// Importações :
import com.example.monaly.domain.model.MediaModel


// Classe selada que define os únicos tipos permitidos na nossa grade de detalhes :
sealed class AlbumDetailItem {


    // Representação do item de texto que cruza a tela dividindo as datas :
    data class DateHeader(val dateText: String) : AlbumDetailItem()


    // Representação do item da miniatura da foto/vídeo :
    data class Media(val mediaData: MediaModel) : AlbumDetailItem()


}