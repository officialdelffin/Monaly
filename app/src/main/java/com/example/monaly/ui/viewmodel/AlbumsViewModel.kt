package com.example.monaly.ui.viewmodel


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


    // Variável observável que guardará a lista de álbuns em constante atualização :
    private val _albumsList = MutableLiveData<List<AlbumModel>>()
    val albumsList: LiveData<List<AlbumModel>> = _albumsList


    // Variável observável para controlar a exibição da animação de carregamento :
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    // Bloco de inicialização disparado automaticamente quando o ViewModel nasce :
    init {


        // Iniciar a observação contínua assim que o gerenciador for instanciado :
        startObservingAlbums()


    }


    // Função que conecta o fluxo contínuo do repositório com a interface visual :
    private fun startObservingAlbums() {


        // Avisando a interface que o carregamento inicial começou :
        _isLoading.value = true


        // Abrindo túnel seguro para escutar o banco de dados respeitando o ciclo de vida :
        viewModelScope.launch {


            // Coletando as atualizações em tempo real emitidas pelo repositório :
            repository.observeAlbums().collect { result ->


                // Entregando a lista pronta e desligando o aviso de carregamento :
                _albumsList.value = result
                _isLoading.value = false


            }


        }


    }


}