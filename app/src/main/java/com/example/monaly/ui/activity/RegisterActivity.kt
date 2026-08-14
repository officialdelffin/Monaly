package com.example.monaly.ui.activity


// Importações :
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.monaly.R
import com.example.monaly.ui.adapter.RegisterPagerAdapter
import com.example.monaly.ui.viewmodel.RegisterViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch


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


        // Mapeando os componentes visuais da interface :
        val viewPager = findViewById<ViewPager2>(R.id.viewPagerRegister)
        val buttonNext = findViewById<MaterialButton>(R.id.buttonRegisterNext)
        val buttonBack = findViewById<AppCompatButton>(R.id.buttonRegisterBack)
        val textTitle = findViewById<TextView>(R.id.textRegisterStepTitle)
        val textDesc = findViewById<TextView>(R.id.textRegisterStepDescription)
        val progressBar = findViewById<ProgressBar>(R.id.progressBarRegister)


        // [MUDANÇA 1] Instanciando o nosso Cofre Compartilhado para armazenar e validar os dados :
        val viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]


        // Bloqueia o arrasto do dedo na tela e injeta o Adaptador com as 3 etapas :
        viewPager.isUserInputEnabled = false
        viewPager.adapter = RegisterPagerAdapter(this)


        // SSOT (Single Source of Truth) que atualiza a interface sempre que a tela muda de verdade :
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            // [AQUI ESTÁ O SEU BLOCO onPageSelected] :
            override fun onPageSelected(position: Int) {


                super.onPageSelected(position)


                when (position) {


                    // E-mail :
                    0 -> {


                        textTitle.text = getString(R.string.register_title_email)
                        textDesc.text = getString(R.string.register_desc_email)
                        buttonBack.visibility = View.INVISIBLE
                        progressBar.progress = 25


                        // [MUDANÇA 2] Bloqueia ou libera o botão baseado no que o usuário já havia digitado no Cofre :
                        buttonNext.isEnabled = viewModel.isEmailValid.value


                    }


                    // Nome e Username :
                    1 -> {


                        textTitle.text = getString(R.string.register_title_profile)
                        textDesc.text = getString(R.string.register_desc_profile)
                        buttonBack.visibility = View.VISIBLE
                        progressBar.progress = 50


                    }


                    // Senha :
                    2 -> {


                        textTitle.text = getString(R.string.register_title_password)
                        textDesc.text = getString(R.string.register_desc_password)
                        buttonBack.visibility = View.VISIBLE
                        progressBar.progress = 75


                    }


                }


            }


        })


        // [MUDANÇA 3] Fica observando a validação do e-mail em tempo real (em milissegundos) :
        lifecycleScope.launch {


            viewModel.isEmailValid.collect { isValid ->


                // Só interfere no botão se o usuário estiver na Etapa 1 (Posição 0) :
                if (viewPager.currentItem == 0) {


                    buttonNext.isEnabled = isValid


                }


            }


        }


        // Configurando o clique do Botão de Avançar :
        buttonNext.setOnClickListener {


            val currentItem = viewPager.currentItem


            // Limite de etapas (por enquanto são 3, então o índice máximo é 2) :
            if (currentItem < 2) {


                viewPager.setCurrentItem(currentItem + 1, true)


            }


        }


        // Configurando o clique do Botão de Voltar :
        buttonBack.setOnClickListener {


            val currentItem = viewPager.currentItem


            if (currentItem > 0) {


                viewPager.setCurrentItem(currentItem - 1, true)


            }


        }


    }


}