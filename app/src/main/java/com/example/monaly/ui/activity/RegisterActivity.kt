package com.example.monaly.ui.activity


// Importações :
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.monaly.R


// Tela responsável por guiar o fluxo progressivo de cadastro de forma segura :
class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)


        // Ajuste das barras de sistema :
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->


            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets


        }


        // Mapeando o nosso Trilho Mestre :
        val viewPager = findViewById<ViewPager2>(R.id.viewPagerRegister)


        // Bloqueia o arrasto do dedo na tela. O usuário é obrigado a preencher e usar o botão Avançar fazendo a segurança da UX:
        viewPager.isUserInputEnabled = false


    }


}