package com.example.monaly.domain.repository

// Importações :
import android.net.Uri
import com.example.monaly.domain.model.MediaModel


// Interface que define o contrato para manipulação de mídias :
interface MediaRepository {


    // Função responsável por enviar a mídia e retornar o modelo salvo :
    suspend fun uploadMedia(uri: Uri, userId: String, mediaType: String, sizeInBytes: Long): MediaModel


}