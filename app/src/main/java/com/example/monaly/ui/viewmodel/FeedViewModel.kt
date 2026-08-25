package com.example.monaly.ui.fragment.navegation


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


// Definindo os estados possíveis da tela durante o envio das mídias :
sealed class UploadState {


    object Idle : UploadState()
    data class Uploading(val current: Int, val total: Int) : UploadState()
    object Success : UploadState()
    data class Error(val message: String) : UploadState()


}


// ViewModel responsável por orquestrar o envio em lote (batch) conectando UI e Repositório e utilizando um valor padrão no construtor para facilitar a injeção do repositório :
class FeedViewModel(private val repository: MediaRepository = MediaRepositoryImplementation()) : ViewModel() {


    // Criando a variável de estado que será observada pelo Fragmento :
    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState


    // Função que recebe a lista de imagens e processa uma a uma :
    fun uploadMediaBatch(uris: List<Uri>) {


        // Buscando o identificador único do usuário logado na sessão oficial do Firebase :
        val userId = FirebaseAuth.getInstance().currentUser?.uid


        // Verificando a segurança da sessão antes de qualquer envio :
        if (userId == null) {


            _uploadState.value = UploadState.Error("Sessão inválida. Faça login novamente.")
            return


        }


        // Iniciando o estado de carregamento para a interface :
        _uploadState.value = UploadState.Uploading(0, uris.size)


        // Abrindo uma corrotina para rodar o processo pesado em segundo plano :
        viewModelScope.launch {


            try {


                var uploadedCount = 0


                // Criando um laço de repetição para enviar cada mídia separadamente :
                for (uri in uris) {


                    // Valores provisórios para simular o tamanho e tipo nesta etapa de validação :
                    val mockType = "image/jpeg"
                    val mockSize = 1024L


                    // Disparando o motor do Repositório para fazer o upload duplo Storage e Firestore :
                    repository.uploadMedia(uri, userId, mockType, mockSize)


                    // Atualizando a contagem e notificando a interface :
                    uploadedCount++
                    _uploadState.value = UploadState.Uploading(uploadedCount, uris.size)


                }


                // Finalizando o processo com sucesso após o fim do laço :
                _uploadState.value = UploadState.Success


            } catch (e: Exception) {


                // Capturando falhas de internet ou permissão :
                _uploadState.value = UploadState.Error(e.message ?: "Erro desconhecido no upload.")


            }


        }


    }


}