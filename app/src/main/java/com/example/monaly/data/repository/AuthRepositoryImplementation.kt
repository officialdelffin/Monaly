package com.example.monaly.data.repository


// Importações :
import com.example.monaly.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await


// Classe que implementa o contrato de autenticação, recebendo a instância do Firebase :
class AuthRepositoryImpl(private val auth: FirebaseAuth) : AuthRepository {


    // Função que recebe o token do Google e tenta logar no Firebase :
    override suspend fun signInWithGoogle(idToken: String): Result<Boolean> {


        return try {


            // Cria a credencial de segurança utilizando o token do Google :
            val credential = GoogleAuthProvider.getCredential(idToken, null)


            // Aciona o Firebase para tentar o login com a credencial e aguarda a resposta (await) :
            auth.signInWithCredential(credential).await()


            // Se o código chegou até aqui sem estourar nenhum erro, o login foi um sucesso :
            Result.success(true)


        } catch (e: Exception) {


            // Se o Firebase recusar ou houver falha de rede, capturamos e retornamos como falha :
            Result.failure(e)


        }


    }


}