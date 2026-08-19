package com.example.monaly.ui.viewmodel


// Importações :
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


// A class que vai guardar os dados do usuário durante todas as etapas do cadastro :
class RegisterViewModel : ViewModel() {


    // Variáveis privadas que guardam os dados sendo o MutableStateFlow que permite alteração interna :
    private val _email = MutableStateFlow("")
    private val _firstName = MutableStateFlow("")
    private val _lastName = MutableStateFlow("")
    private val _username = MutableStateFlow("")
    private val _password = MutableStateFlow("")


    // Variável de estado para validar a etapa um :
    private val _isEmailValid = MutableStateFlow(false)


    // Variáveis públicas e imutáveis para a Activity ler os dados com segurança sendo o asStateFlow :
    val email: StateFlow<String> = _email.asStateFlow()
    val firstName: StateFlow<String> = _firstName.asStateFlow()
    val lastName: StateFlow<String> = _lastName.asStateFlow()
    val username: StateFlow<String> = _username.asStateFlow()
    val password: StateFlow<String> = _password.asStateFlow()


    // Variável pública de validação que a Activity vai observar :
    val isEmailValid: StateFlow<Boolean> = _isEmailValid.asStateFlow()


    // Atualiza e valida o formato do e-mail em tempo real :
    fun validateEmail(newEmail: String) {


        _email.value = newEmail


        // Regex rigorosa que obriga ter texto, a arroba (@), o provedor e o domínio .com :
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"


        // Se o formato estiver correto, ele guarda "true" e libera a próxima tela :
        _isEmailValid.value = newEmail.matches(emailRegex.toRegex())


    }


    // Funções para os Fragments guardarem os textos dentro do cofre :
    fun updateProfile(newFirstName: String, newLastName: String, newUsername: String) {


        _firstName.value = newFirstName
        _lastName.value = newLastName
        _username.value = newUsername


    }


    //  Variável de estado para validar se a Etapa 2 está completa :
    private val _isProfileValid = MutableStateFlow(false)


    // Variável pública de validação que a Activity vai observar para a Etapa 2 :
    val isProfileValid: StateFlow<Boolean> = _isProfileValid.asStateFlow()


    // Valida se o Nome, Sobrenome e Username foram preenchidos corretamente :
    fun validateProfile(firstName: String, lastName: String, username: String) {


        updateProfile(firstName, lastName, username)


        // Regra simples de preenchimento: Nenhum dos três campos pode estar vazio :
        val isValid = firstName.isNotBlank() && lastName.isNotBlank() && username.isNotBlank()


        _isProfileValid.value = isValid


    }


    // Variável de estado para validar se a Etapa 3 (Senha) está correta :
    private val _isPasswordValid = MutableStateFlow(false)


    // Variável pública de validação que a Activity vai observar para a Etapa 3 :
    val isPasswordValid: StateFlow<Boolean> = _isPasswordValid.asStateFlow()


    // Valida a senha usando Regex para garantir que tenha apenas letras e números (sem símbolos ou espaços) :
    fun validatePassword(password: String) {

        updatePassword(password)

        // Regex que aceita apenas letras (maiúsculas/minúsculas) e números, com tamanho mínimo de 6 caracteres :
        val passwordRegex = "^[a-zA-Z0-9]{6,}\$"

        _isPasswordValid.value = password.matches(passwordRegex.toRegex())

    }


    fun updatePassword(newPassword: String) {


        _password.value = newPassword


    }


    // Controla se a tela deve mostrar uma bolinha girando (carregamento) :
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    // Avisa a Activity sobre o resultado final (Sucesso ou o motivo do Erro) :
    private val _registrationState = MutableStateFlow<String?>(null)
    val registrationState: StateFlow<String?> = _registrationState.asStateFlow()


    // Função final acionada pelo botão da Etapa 4 :
    fun createAccount() {


        // Acende o carregamento para travar a tela :
        _isLoading.value = true


        val currentEmail = _email.value
        val currentPassword = _password.value


        // Bate na porta do Authentication para criar o login :
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(currentEmail, currentPassword)


            .addOnSuccessListener { authResult ->


                // Se deu certo, pegamos o ID único gerado para este celular :
                val uid = authResult.user?.uid


                if (uid != null) {


                    // Empacotamos os dados do perfil em um mapa para o banco :
                    val userProfile = hashMapOf(


                        "firstName" to _firstName.value,
                        "lastName" to _lastName.value,
                        "username" to _username.value,
                        "email" to currentEmail


                    )


                    // Bate na porta do Firestore, cria um documento com o UID e salva o perfil :
                    FirebaseFirestore.getInstance().collection("users").document(uid).set(userProfile)


                        .addOnSuccessListener {


                            // Tudo perfeito! Apaga o carregamento e avisa a Activity do sucesso :
                            _isLoading.value = false
                            _registrationState.value = "SUCESSO"


                        }


                        .addOnFailureListener { error ->


                            // Se o banco falhar, avisamos a Activity :
                            _isLoading.value = false
                            _registrationState.value = "Erro ao salvar perfil: ${error.message}"


                        }


                }


            }


            .addOnFailureListener { error ->


                // Se a criação da conta falhar (ex: email já existe), avisamos a Activity :
                _isLoading.value = false
                _registrationState.value = "Erro na conta: ${error.message}"


            }


    }


}