package com.example.monaly.ui.auth


// Importações necessárias para a ViewModel, Corrotinas e Firebase :
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monaly.domain.repository.AuthRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


// Definindo os estados possíveis da tela de login :
sealed class AuthState {


    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()


}


// ViewModel responsável por gerenciar a lógica de login e conectar a UI ao Repositório :
class AuthViewModel(private val repository: AuthRepository) : ViewModel() {


    // Criando a variável de estado que será observada pela Activity :
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState


    // Função responsável por processar o token e criar o perfil do usuário :
    fun signInWithGoogle(token: String) {


        // Atualizando o estado para indicar carregamento na interface :
        _authState.value = AuthState.Loading


        viewModelScope.launch {


            try {


                // Autenticando o token diretamente no repositório para obter o usuário :
                val user = repository.signInWithGoogle(token)


                if (user != null) {


                    // Instanciando o Firestore e buscando a referência do documento do usuário :
                    val db = FirebaseFirestore.getInstance()
                    val userRef = db.collection("users").document(user.uid)


                    // Lendo o documento de forma suspensa utilizando a extensão await() :
                    val document = userRef.get().await()


                    // Verificando se o perfil ainda não existe no banco de dados :
                    if (!document.exists()) {


                        // Extraindo o nome de exibição do Google :
                        val displayName = user.displayName ?: "Usuario"


                        // Formatando o nome para manter apenas caracteres alfanuméricos :
                        val baseName = displayName.lowercase().replace(Regex("[^a-z0-9]"), "")


                        // Gerando o sufixo numérico aleatório para compor o username :
                        val randomSuffix = (1000..9999).random()
                        val generatedUsername = "@$baseName$randomSuffix"


                        // Mapeando as informações do novo perfil :
                        val newUserProfile = hashMapOf(
                            "name" to displayName,
                            "username" to generatedUsername,
                            "email" to user.email
                        )


                        // Salvando o novo perfil no Firestore de forma suspensa :
                        userRef.set(newUserProfile).await()


                    }


                    // Notificando o sucesso da operação para a interface :
                    _authState.value = AuthState.Success


                } else {


                    // Notificando erro caso o retorno do usuário seja nulo :
                    _authState.value = AuthState.Error("Erro ao obter dados da conta Google.")


                }


            } catch (e: Exception) {


                // Capturando e exibindo qualquer erro ocorrido durante o processo :
                _authState.value = AuthState.Error(e.message ?: "Erro desconhecido no login.")


            }


        }


    }


}