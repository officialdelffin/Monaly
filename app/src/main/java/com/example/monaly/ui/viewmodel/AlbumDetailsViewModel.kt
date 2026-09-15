package com.example.monaly.ui.viewmodel


// Importações :
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monaly.data.repository.MediaRepositoryImplementation
import com.example.monaly.domain.repository.MediaRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


// ViewModel responsável por orquestrar o envio de mídias vinculadas a um álbum específico :
class AlbumDetailsViewModel(private val repository: MediaRepository = MediaRepositoryImplementation()) : ViewModel() {


    // Criando a variável de estado que será observada pela tela de detalhes :
    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState


    // Função que recebe a lista de imagens e o ID do álbum para processamento em lote :
    fun uploadMediaToAlbum(uris: List<Uri>, albumId: String) {


        // Buscando o identificador único do usuário logado na sessão oficial :
        val userId = FirebaseAuth.getInstance().currentUser?.uid


        // Verificando a segurança da sessão antes de qualquer envio :
        if (userId == null) {


            _uploadState.value = UploadState.Error("Sessão inválida. Faça login novamente.")
            return


        }


        // Iniciando o estado de carregamento para exibir a barra de progresso :
        _uploadState.value = UploadState.Uploading(0, uris.size)


        // Abrindo uma corrotina para rodar o processo pesado em segundo plano sem travar a interface :
        viewModelScope.launch {


            try {


                var uploadedCount = 0


                // Criando um laço de repetição para enviar cada mídia vinculando ao álbum :
                for (uri in uris) {


                    // Valores provisórios base para simular tipo e tamanho :
                    val mockType = "image/jpeg"
                    val mockSize = 1024L


                    // Disparando o motor do Repositório injetando o ID do álbum na nuvem :
                    repository.uploadMedia(uri, userId, mockType, mockSize, albumId)


                    // Atualizando a contagem silenciosamente :
                    uploadedCount++
                    _uploadState.value = UploadState.Uploading(uploadedCount, uris.size)


                }


                // Finalizando o processo com sucesso após o término do laço :
                _uploadState.value = UploadState.Success


            } catch (e: Exception) {


                // Capturando falhas de conexão :
                _uploadState.value = UploadState.Error(e.message ?: "Erro desconhecido no upload.")


            }


        }


    }


}