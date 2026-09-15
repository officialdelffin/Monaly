package com.example.monaly.ui.viewmodel


// Importações :
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monaly.data.repository.MediaRepositoryImplementation
import com.example.monaly.domain.model.AlbumDetailItem
import com.example.monaly.domain.repository.MediaRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale


// ViewModel responsável por gerenciar uploads e downloads da tela de detalhes :
class AlbumDetailsViewModel(private val repository: MediaRepository = MediaRepositoryImplementation()) : ViewModel() {


    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState


    // Variável reativa que guardará a lista processada e pronta para a tela desenhar :
    private val _mediaList = MutableStateFlow<List<AlbumDetailItem>>(emptyList())
    val mediaList: StateFlow<List<AlbumDetailItem>> = _mediaList


    // Função para buscar os dados brutos e transformar no formato exigido pela interface :
    fun loadMedia(albumId: String) {


        viewModelScope.launch {


            try {


                val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
                val rawMedia = repository.getMediaByAlbum(userId, albumId)


                // Formatador de data para criar os textos dos cabeçalhos :
                val sdf = SimpleDateFormat("dd 'de' MMMM 'de' yyyy", Locale("pt", "BR"))


                // Agrupando as mídias brutas com base na data formatada :
                val groupedMedia = rawMedia.groupBy { media ->


                    sdf.format(media.createdAt)


                }


                val finalItems = mutableListOf<AlbumDetailItem>()


                // Construindo a lista intercalando cabeçalhos de texto e as mídias correspondentes :
                for ((dateString, mediaList) in groupedMedia) {


                    finalItems.add(AlbumDetailItem.DateHeader("Atualizado em $dateString"))


                    mediaList.forEach { media ->


                        finalItems.add(AlbumDetailItem.Media(media))


                    }


                }


                // Atualizando o estado reativo para notificar a tela :
                _mediaList.value = finalItems


            } catch (e: Exception) {


                // Lidar com possíveis erros de rede de forma silenciosa na grade :
            }


        }


    }


    fun uploadMediaToAlbum(uris: List<Uri>, albumId: String) {


        val userId = FirebaseAuth.getInstance().currentUser?.uid


        if (userId == null) {


            _uploadState.value = UploadState.Error("Sessão inválida. Faça login novamente.")
            return


        }


        _uploadState.value = UploadState.Uploading(0, uris.size)


        viewModelScope.launch {


            try {


                var uploadedCount = 0


                for (uri in uris) {


                    val mockType = "image/jpeg"
                    val mockSize = 1024L
                    repository.uploadMedia(uri, userId, mockType, mockSize, albumId)
                    uploadedCount++
                    _uploadState.value = UploadState.Uploading(uploadedCount, uris.size)


                }


                _uploadState.value = UploadState.Success


                // Gatilho automático: forçando a busca de dados logo após o sucesso do upload :
                loadMedia(albumId)


            } catch (e: Exception) {


                _uploadState.value = UploadState.Error(e.message ?: "Erro desconhecido no upload.")


            }


        }


    }


    fun resetState() {


        _uploadState.value = UploadState.Idle


    }


}