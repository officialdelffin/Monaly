package com.example.monaly.ui.activity


// Importações :
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.monaly.R
import com.example.monaly.data.repository.AuthRepositoryImpl
import com.example.monaly.ui.auth.AuthState
import com.example.monaly.ui.auth.AuthViewModel
import com.example.monaly.ui.auth.GoogleAuthUiClient
import com.example.monaly.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


// Classe responsável pela manipulação dos elementos da activity de login :
class LoginActivity : AppCompatActivity() {


    // Variáveis que vão guardar o nosso ViewModel e o Cliente do Google :
    private lateinit var authViewModel: AuthViewModel
    private lateinit var googleAuthUiClient: GoogleAuthUiClient


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // Inicializando o Firebase e o nosso Repositório sendo a camada data :
        val firebaseAuth = FirebaseAuth.getInstance()
        val authRepository = AuthRepositoryImpl(firebaseAuth)


        // Inicializando o Cliente do Google sendo a camada UI :
        val credentialManager = CredentialManager.create(this)
        googleAuthUiClient = GoogleAuthUiClient(this, credentialManager)


        // Criando a Fábrica para construir o ViewModel :
        val factory = object : ViewModelProvider.Factory {


            override fun <T : ViewModel> create(modelClass: Class<T>): T {


                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(authRepository) as T


            }


        }


        // Recebendo o ViewModel pronto :
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]


        // Mapeando o botão da interface :
        val buttonLogin = findViewById<AppCompatButton>(R.id.buttonLoginGoogle)


        // Configurando a ação de clique do botão de Entrar :
        buttonLogin.setOnClickListener {


            lifecycleScope.launch {


                // Abre a bandeja nativa de contas do Google e aguarda a escolha :
                val token = googleAuthUiClient.signIn()


                // Se o usuário selecionou uma conta e o Google devolveu o token :
                if (token != null) {


                    // Mandamos o nosso ViewModel enviar esse token lá para o Firebase :
                    authViewModel.signInWithGoogle(token)


                } else {


                    // Se o usuário fechou a janela sem escolher nada :
                    Toast.makeText(this@LoginActivity, "Login cancelado", Toast.LENGTH_SHORT).show()


                }


            }


        }


        // Observando as mudanças de estado do ViewModel para reagir na tela :
        lifecycleScope.launch {


            authViewModel.authState.collect { state ->


                when (state) {


                    is AuthState.Idle -> {


                        // Estado inicial, não fazemos nada :


                    }


                    is AuthState.Loading -> {


                        // Enquanto carrega, bloqueamos o clique para evitar múltiplos logins :
                        buttonLogin.isEnabled = false
                        buttonLogin.text = "Autenticando..."


                    }


                    is AuthState.Success -> {


                        // Se o Firebase aprovou, avisamos e navegamos para a tela Principal :
                        Toast.makeText(this@LoginActivity, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()


                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)


                        // Encerramos a tela de login para o usuário não voltar pra cá :
                        finish()


                    }


                    is AuthState.Error -> {


                        // Se o Firebase recusou, liberamos o botão novamente e mostramos o erro :
                        buttonLogin.isEnabled = true
                        buttonLogin.text = getString(R.string.login_button)
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_LONG).show()


                    }


                }


            }


        }


    }


}