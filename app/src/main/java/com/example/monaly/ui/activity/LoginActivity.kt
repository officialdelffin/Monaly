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
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


// Tela de login enxuta, focada 100% na entrada pelo Google :
class LoginActivity : AppCompatActivity() {


    // Preparando as variáveis do meu cérebro viewModel e do cliente do Google :
    private lateinit var authViewModel: AuthViewModel
    private lateinit var googleAuthUiClient: GoogleAuthUiClient


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        // Ligando os motores do Firebase e da camada de dados :
        val firebaseAuth = FirebaseAuth.getInstance()
        val authRepository = AuthRepositoryImpl(firebaseAuth)


        // Instanciando o cliente de credenciais que vai abrir a gaveta do Google na tela :
        val credentialManager = CredentialManager.create(this)
        googleAuthUiClient = GoogleAuthUiClient(this, credentialManager)


        // Criando a fábrica pra conseguir injetar o repositório dentro do meu ViewModel :
        val factory = object : ViewModelProvider.Factory {


            override fun <T : ViewModel> create(modelClass: Class<T>): T {


                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(authRepository) as T


            }


        }


        // Pegando o ViewModel prontinho pra usar :
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]


        // Capturando o botão do Google no XML :
        val buttonLogin = findViewById<AppCompatButton>(R.id.buttonLoginGoogle)


        // Ao clicar no buttonLogin :
        buttonLogin.setOnClickListener {


            lifecycleScope.launch {


                // Abre a bandeja de contas do Google e espera ele escolher uma :
                val token = googleAuthUiClient.signIn()


                // Se ele escolheu uma conta e o Google me devolveu o token de segurança :
                if (token != null) {


                    // Avisando pro ViewModel mandar isso lá pro servidor do Firebase aprovar :
                    authViewModel.signInWithGoogle(token)


                } else {


                    // Se ele clicou fora ou cancelou a janela :
                    Toast.makeText(this@LoginActivity, "Login cancelado", Toast.LENGTH_SHORT).show()


                }


            }


        }


        // Ficando de olho nas respostas que o Firebase manda pro ViewModel :
        lifecycleScope.launch {


            authViewModel.authState.collect { state ->


                when (state) {


                    is AuthState.Idle -> { }


                    // Enquanto processa ele trava o botão pra ele não clicar mil vezes :
                    is AuthState.Loading -> {


                        buttonLogin.isEnabled = false
                        buttonLogin.text = "Autenticando..."


                    }


                    // Se o firebase pertimir entrar :
                    is AuthState.Success -> {


                        Toast.makeText(this@LoginActivity, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()


                        // Encaminha o usuário para a tela inicial :
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)


                        // Fecho a porta do login pra ele não voltar pra cá sem querer :
                        finish()


                    }


                    // Se o Firebase barrou :
                    is AuthState.Error -> {


                        // Libera o botão de novo pro cara tentar outra vez e mostro o erro :
                        buttonLogin.isEnabled = true
                        buttonLogin.text = getString(R.string.login_button)
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_LONG).show()


                    }


                }


            }


        }


    }


}