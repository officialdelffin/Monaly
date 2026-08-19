package com.example.monaly.domain.repository


// Interface que define o contrato de autenticação para o aplicativo Monaly :
interface AuthRepository {


    // Função suspensa que recebe o token do Google e tenta fazer o login no Firebase, retornando se deu certo ou não :
    suspend fun signInWithGoogle(idToken: String): Result<Boolean>


}