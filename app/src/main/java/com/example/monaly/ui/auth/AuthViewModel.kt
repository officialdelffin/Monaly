package com.example.monaly.ui.auth


// Importações :
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monaly.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


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


    // Função que recebe o token do Google e aciona o repositório em segundo plano :
    fun signInWithGoogle(idToken: String) {


        viewModelScope.launch {


            // Avisa a tela que o carregamento começou, sendo útil para mostrar um ProgressBar :
            _authState.value = AuthState.Loading


            // Tenta fazer o login usando a função que criamos no repositório :
            val result = repository.signInWithGoogle(idToken)


            // Analisa a resposta do Firebase e atualiza o estado final :
            result.fold(


                onSuccess = {


                    _authState.value = AuthState.Success


                },
                onFailure = {


                    _authState.value = AuthState.Error(it.message ?: "Erro desconhecido ao fazer login")


                }


            )


        }


    }


}