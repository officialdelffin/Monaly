package com.example.monaly.ui.viewmodel // Ajustar para o pacote correto do projeto :


// Importações :
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monaly.domain.model.AlbumModel
import com.example.monaly.domain.repository.AlbumRepository
import kotlinx.coroutines.launch


// Gerenciador de dados exclusivo para a tela de lista de álbuns :
class AlbumsViewModel : ViewModel() {


    // Instanciando a conexão com o banco de dados :
    private val repository = AlbumRepository()


    // Variável observável que guardará a lista de álbuns baixada da nuvem :
    private val _albumsList = MutableLiveData<List<AlbumModel>>()
    val albumsList: LiveData<List<AlbumModel>> = _albumsList


    // Variável observável para controlar a exibição da animação de carregamento :
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    // Bloco de inicialização disparado automaticamente quando o ViewModel nasce :
    init {


        // Disparar a busca na nuvem imediatamente :
        fetchAlbums()


    }


    // Função responsável por acionar o trabalho em segundo plano :
    fun fetchAlbums() {


        // Avisar a interface que o carregamento começou :
        _isLoading.value = true


        // Abrir túnel seguro para tarefas pesadas :
        viewModelScope.launch {


            // Aguardar a resposta do Firebase :
            val result = repository.getAlbums()


            // Entregar a lista pronta e desligar o aviso de carregamento :
            _albumsList.value = result
            _isLoading.value = false


        }


    }


}