package com.example.monaly.ui.activity


// Importações :
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.monaly.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth


// Tela inicial que atua fazendo a conferencia se há login feito ou não :
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        // O Firebase gerencia os tokens de sessão automaticamente e o currentUser verifica se há um token válido salvo e criptografado no celular :
        val currentUser = FirebaseAuth.getInstance().currentUser


        // Se não for nulo, tem usuário, roteamos direto para a MainActivity pulando Onboarding e Login :
        if (currentUser != null) {


            startActivity(Intent(this, MainActivity::class.java))


        }


        // Se for nulo, ninguém logado, mandamos para o fluxo inicial do Onboarding :
        else {


            startActivity(Intent(this, OnboardingActivity::class.java))


        }


        // Encerramos a Splash para destruí-la da memória assim impedindo que o usuário volte para essa tela preta se apertar o botão 'Voltar' do celular :
        finish()


    }


}