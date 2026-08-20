package com.example.monaly.ui.activity


// Importações :
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.monaly.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth


// Tela fantasma que decide pra onde o usuário vai antes de carregar qualquer visual :
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        // Pergunta o firebase se já tem alguém com a sessão ativa nesse celular :
        val currentUser = FirebaseAuth.getInstance().currentUser


        // Se devolver algo diferente de nulo, o usuário já tá logado, mando direto pro feed principal :
        if (currentUser != null) {


            startActivity(Intent(this, MainActivity::class.java))


        }


        // Se for nulo, é usuário novo ou deslogado, então jogo pro fluxo de boas vindas :
        else {


            startActivity(Intent(this, OnboardingActivity::class.java))


        }


        // Destruindo a tela da memória pro usuário não conseguir voltar pra ela apertando o botão de voltar do celular :
        finish()


    }


}