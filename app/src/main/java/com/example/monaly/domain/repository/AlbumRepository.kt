package com.example.monaly.domain.repository


// Importações
import android.net.Uri
import com.example.monaly.domain.model.AlbumModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID


// Repositório exclusivo para gerenciar as operações de álbuns no banco de dados :
class AlbumRepository {


    // Instanciando as ferramentas do Firebase Firestore (Textos) e Storage (Arquivos) :
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()


    // Função assíncrona para criar o álbum sem travar a interface do usuário :
    suspend fun createAlbum(album: AlbumModel, coverUri: Uri): Boolean {


        // Tentando executar o fluxo de envio :
        return try {


            // NOVO: Obtendo o ID do usuário autenticado no momento exato da criação :
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""


            // Gerando um nome único para a imagem e fazendo o upload para o Storage :
            val fileName = UUID.randomUUID().toString()
            val imageRef = storage.reference.child("albums_covers/$fileName.jpg")


            // Aguardando o upload finalizar :
            imageRef.putFile(coverUri).await()


            // Resgatando o link público da imagem gerada pela nuvem :
            val downloadUrl = imageRef.downloadUrl.await().toString()


            // Gerando um ID único para a gaveta do álbum no Firestore :
            val newAlbumId = firestore.collection("albums").document().id


            // ATENÇÃO: Injetando o ID do usuário (ownerId), além do ID do álbum e URL da foto :
            val finalAlbum = album.copy(


                id = newAlbumId,
                coverUrl = downloadUrl,
                ownerId = currentUserId


            )


            // Salvando o modelo de dados completo na coleção "albums" :
            firestore.collection("albums").document(newAlbumId).set(finalAlbum).await()


            // Retornando verdadeiro para indicar que o processo inteiro foi um sucesso :
            true


        } catch (e: Exception) {


            // Retornando falso caso ocorra queda de rede ou erro no Firebase :
            false


        }


    }


    // Função atualizada para buscar a lista filtrada de álbuns no Firestore :
    suspend fun getAlbums(): List<AlbumModel> {


        return try {


            // Obtendo o ID único do usuário autenticado no aplicativo :
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""


            // Consultando a coleção exigindo que o dono seja o usuário atual :
            val snapshot = firestore.collection("albums")


                .whereEqualTo("ownerId", currentUserId)
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()


            // Convertendo os documentos filtrados para o modelo oficial :
            snapshot.documents.mapNotNull { document ->


                document.toObject(AlbumModel::class.java)


            }


        } catch (e: Exception) {


            // Retornando lista vazia em caso de falha de segurança ou rede :
            emptyList()


        }


    }


}