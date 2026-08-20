package com.example.monaly.ui.auth


// Importações :
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monaly.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.google.firebase.firestore.FirebaseFirestore


// Definindo os estados possíveis da nossa tela de login :
sealed class AuthState {


    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()


}


// ViewModel responsável por gerenciar a lógica de login e conectar a UI ao Repositório :
class AuthViewModel(private val repository: AuthRepository) : ViewModel() {


    // Criando a variável de estado que será observada pela nossa Activity :
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState


    // Função responsável por processar o token e criar o perfil invisível :
    fun signInWithGoogle(token: String) {


        // Atualizando o estado para travar o botão na interface :
        _authState.value = AuthState.Loading


        viewModelScope.launch {


            try {


                // Autenticando o token recebido no repositório do Firebase Auth usando o nome correto da variável :
                val authResult = repository.signInWithGoogle(token)
                val user = authResult.user


                if (user != null) {


                    // Instanciando o banco de dados para verificar o perfil :
                    val db = FirebaseFirestore.getInstance()
                    val userRef = db.collection("users").document(user.uid)


                    // Verificando a existência do documento do usuário no Firestore :
                    userRef.get().addOnSuccessListener { document ->


                        // Validando se é o primeiro login deste usuário :
                        if (!document.exists()) {


                            // Extraindo o nome do Google ou definindo um padrão alternativo :
                            val displayName = user.displayName ?: "Usuario"


                            // Formatando o nome para manter apenas letras minúsculas e números :
                            val baseName = displayName.lowercase().replace(Regex("[^a-z0-9]"), "")


                            // Gerando um sufixo aleatório de 4 dígitos para garantir exclusividade :
                            val randomSuffix = (1000..9999).random()
                            val generatedUsername = "@$baseName$randomSuffix"


                            // Empacotando os dados extraídos para salvar no banco :
                            val newUserProfile = hashMapOf(
                                "name" to displayName,
                                "username" to generatedUsername,
                                "email" to user.email
                            )


                            // Salvando o novo perfil no Firestore e liberando o acesso em seguida :
                            userRef.set(newUserProfile)
                                .addOnSuccessListener {


                                    _authState.value = AuthState.Success


                                }
                                .addOnFailureListener { error ->


                                    // Tratando falhas na gravação dos dados iniciais :
                                    _authState.value = AuthState.Error("Erro ao criar perfil: ${error.message}")


                                }


                        } else {


                            // Aprovando o login diretamente caso o usuário já possua perfil salvo :
                            _authState.value = AuthState.Success


                        }


                    }.addOnFailureListener { error ->


                        // Tratando falhas de conexão com o banco de dados durante a leitura :
                        _authState.value = AuthState.Error("Erro de conexão: ${error.message}")


                    }


                } else {


                    // Tratando a falha de retorno nulo do Firebase Auth :
                    _authState.value = AuthState.Error("Erro ao obter dados da conta Google.")


                }


            } catch (e: Exception) {


                // Capturando e exibindo exceções não mapeadas durante a requisição :
                _authState.value = AuthState.Error(e.message ?: "Erro desconhecido no login.")


            }


        }


    }


}