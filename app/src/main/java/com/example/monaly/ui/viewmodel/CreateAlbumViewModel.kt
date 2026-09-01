package com.example.monaly.ui.viewmodel


// Importações :
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monaly.domain.model.AlbumModel
import com.example.monaly.domain.repository.AlbumRepository
import kotlinx.coroutines.launch


// Classe auxiliar blindada para mapear os três estados possíveis da tela :
sealed class UploadState {


    object Loading : UploadState()
    object Success : UploadState()
    data class Error(val message: String) : UploadState()


}


// ViewModel responsável por gerenciar os dados e a lógica da tela de Criação de Álbuns :
class CreateAlbumViewModel : ViewModel() {


    // Instanciando o repositório para fazer o trabalho pesado de nuvem :
    private val repository = AlbumRepository()


    // Variáveis observáveis para avisar a tela sobre o status do envio (Loading, Success ou Error) :
    private val _uploadState = MutableLiveData<UploadState>()
    val uploadState: LiveData<UploadState> = _uploadState


    // Função chamada pelo botão de salvar na interface gráfica :
    fun createAlbum(album: AlbumModel, coverUri: Uri) {


        // Avisando a tela que o processo começou para exibir a barra de progresso :
        _uploadState.value = UploadState.Loading


        // Iniciando um trabalho em segundo plano amarrado ao ciclo de vida desta tela :
        viewModelScope.launch {


            // Chamando o repositório para subir a imagem e salvar no Firestore :
            val isSuccess = repository.createAlbum(album, coverUri)


            // Atualizando a interface com o resultado final do servidor :
            if (isSuccess) {


                _uploadState.value = UploadState.Success


            }


            else {


                _uploadState.value = UploadState.Error("Falha ao criar o álbum. Verifique sua conexão.")


            }


        }


    }


}