package com.example.monaly.data.repository


// Importações :
import android.net.Uri
import com.example.monaly.domain.model.MediaModel
import com.example.monaly.domain.repository.MediaRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.UUID


// Implementação do repositório responsável por interagir com o Firebase Storage e Firestore :
class MediaRepositoryImplementation : MediaRepository {


    // Função que executa o upload duplo no Storage e depois no Firestore de forma segura :
    override suspend fun uploadMedia(uri: Uri, userId: String, mediaType: String, sizeInBytes: Long, albumId: String?): MediaModel {


        // Gerando um nome único para o arquivo para evitar colisões :
        val fileName = UUID.randomUUID().toString()


        // Criando a referência da pasta isolada do usuário no Firebase Storage :
        val storageRef = FirebaseStorage.getInstance().reference.child("users").child(userId).child("media").child(fileName)


        // Realizando o upload do arquivo físico de forma suspensa :
        storageRef.putFile(uri).await()


        // Resgatando o link público de download gerado pelo Storage :
        val downloadUrl = storageRef.downloadUrl.await().toString()


        // Instanciando o banco de dados para salvar os metadados da imagem :
        val db = FirebaseFirestore.getInstance()
        val mediaDocRef = db.collection("users").document(userId).collection("media").document()


        // Montando o modelo de dados repassando o ID do álbum para criar o vínculo correto :
        val mediaModel = MediaModel(


            id = mediaDocRef.id,
            userId = userId,
            albumId = albumId,
            mediaUrl = downloadUrl,
            mediaType = mediaType,
            sizeInBytes = sizeInBytes,
            createdAt = Date()


        )


        // Salvando o documento na planilha do Firestore de forma suspensa :
        mediaDocRef.set(mediaModel).await()


        // Retornando o modelo concluído para a camada superior do aplicativo :
        return mediaModel


    }


    // Função que consulta o Firestore filtrando as mídias pelo ID do álbum e ordenando pelas mais recentes :
    override suspend fun getMediaByAlbum(userId: String, albumId: String): List<MediaModel> {


        val db = FirebaseFirestore.getInstance()


        // Realizando a busca na coleção de mídias do usuário :
        val snapshot = db.collection("users")
            .document(userId)
            .collection("media")
            .whereEqualTo("albumId", albumId)
            .get()
            .await()


        // Convertendo os documentos encontrados para o modelo de dados e ordenando por data :
        return snapshot.toObjects(MediaModel::class.java).sortedByDescending { it.createdAt }


    }


}