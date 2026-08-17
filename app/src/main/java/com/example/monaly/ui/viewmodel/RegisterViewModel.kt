package com.example.monaly.ui.viewmodel


// Importações :
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


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


    fun updatePassword(newPassword: String) {


        _password.value = newPassword


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


}