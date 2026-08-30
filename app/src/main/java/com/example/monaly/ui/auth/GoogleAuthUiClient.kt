package com.example.monaly.ui.auth


// Importações :
import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.example.monaly.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential


// Classe responsável por exibir a interface nativa de contas do Google no Android :
class GoogleAuthUiClient(private val context: Context, private val credentialManager: CredentialManager) {


    // Função que abre a bandeja do Google e retorna o Token da conta escolhida (ou nulo se falhar) :
    suspend fun signIn(): String? {


        return try {


            // Configura as opções do Google puxando a chave dinamicamente do JSON gerado :
            val googleIdOption = GetGoogleIdOption.Builder()


                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .setAutoSelectEnabled(true)
                .build()


            // Monta a requisição final com as opções criadas acima :
            val request = GetCredentialRequest.Builder()


                .addCredentialOption(googleIdOption)
                .build()


            // Executa a requisição fazendo a bandeja deslizar na tela do usuário :
            val result = credentialManager.getCredential(context, request)


            val credential = result.credential


            // Verifica se a credencial recebida é do tipo Google e extrai o Token :
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {


                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                googleIdTokenCredential.idToken


            }


            else {


                null


            }


        } catch (e: Exception) {


            // Imprimindo o erro real em vermelho no Logcat do Android Studio para facilitar diagnósticos :
            android.util.Log.e("AuthError", "Falha na credencial do Google: ${e.message}")


            // Em caso de erro retornamos nulo mantendo a estabilidade da interface :
            null


        }


    }


}