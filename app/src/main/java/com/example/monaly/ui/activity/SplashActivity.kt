package com.example.monaly.ui.activity


// Importações que preciso pra fazer as transições de tela e checar o login :
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.monaly.ui.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth


// Minha tela fantasma que decide pra onde o usuário vai antes de carregar qualquer visual :
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)


        // Vou perguntar pro Firebase se já tem alguém com a sessão ativa nesse celular :
        val currentUser = FirebaseAuth.getInstance().currentUser


        // Se me devolver algo diferente de nulo, o cara já tá logado, mando direto pro feed principal :
        if (currentUser != null) {


            startActivity(Intent(this, MainActivity::class.java))


        }


        // Se for nulo, é usuário novo ou deslogado, então jogo pro fluxo de boas vindas :
        else {


            startActivity(Intent(this, OnboardingActivity::class.java))


        }


        // Mato essa tela da memória pro usuário não conseguir voltar pra ela apertando o botão de voltar do celular :
        finish()


    }


}