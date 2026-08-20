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


        // Instanciando o nosso cofre compartilhado para armazenar e validar os dados :
        val viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]


        // Bloqueia o arrasto do dedo na tela e injeta o Adaptador com as 4 etapas agora :
        viewPager.isUserInputEnabled = false
        viewPager.adapter = RegisterPagerAdapter(this)


        // SSOT que atualiza a interface sempre que a tela muda de verdade :
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageSelected(position: Int) {


                super.onPageSelected(position)


                when (position) {


                    // E-mail :
                    0 -> {


                        textTitle.text = getString(R.string.register_title_email)
                        textDesc.text = getString(R.string.register_desc_email)
                        buttonBack.visibility = View.INVISIBLE
                        progressBar.progress = 25
                        buttonNext.text = getString(R.string.register_button_next)


                        setNextButtonState(buttonNext, viewModel.isEmailValid.value)


                    }


                    // Nome e Username :
                    1 -> {


                        textTitle.text = getString(R.string.register_title_profile)
                        textDesc.text = getString(R.string.register_desc_profile)
                        buttonBack.visibility = View.VISIBLE
                        progressBar.progress = 50
                        buttonNext.text = getString(R.string.register_button_next)


                        setNextButtonState(buttonNext, viewModel.isProfileValid.value)


                    }


                    // Senha :
                    2 -> {


                        textTitle.text = getString(R.string.register_title_password)
                        textDesc.text = getString(R.string.register_desc_password)
                        buttonBack.visibility = View.VISIBLE
                        progressBar.progress = 75
                        buttonNext.text = getString(R.string.register_button_next)


                        setNextButtonState(buttonNext, viewModel.isPasswordValid.value)


                    }


                    // Adicionamos o estado para a Etapa 4 (Resumo Final) :
                    3 -> {


                        textTitle.text = getString(R.string.register_title_summary)
                        textDesc.text = getString(R.string.register_desc_summary)
                        buttonBack.visibility = View.VISIBLE
                        progressBar.progress = 100

                        // O botão muda para "Concluir" e fica sempre liberado para enviar :
                        buttonNext.text = "Concluir"
                        setNextButtonState(buttonNext, true)


                    }


                }


            }


        })


        // Fica observando a validação do e-mail em tempo real :
        lifecycleScope.launch {


            viewModel.isEmailValid.collect { isValid ->


                if (viewPager.currentItem == 0) {


                    setNextButtonState(buttonNext, isValid)


                }


            }


        }


        // Fica observando a validação do perfil em tempo real :
        lifecycleScope.launch {


            viewModel.isProfileValid.collect { isValid ->


                if (viewPager.currentItem == 1) {


                    setNextButtonState(buttonNext, isValid)


                }


            }


        }


        // Fica observando a validação da senha em tempo real :
        lifecycleScope.launch {


            viewModel.isPasswordValid.collect { isValid ->


                if (viewPager.currentItem == 2) {


                    setNextButtonState(buttonNext, isValid)


                }


            }


        }


        // Observa o carregamento para travar a tela e dar feedback ao usuário :
        lifecycleScope.launch {


            viewModel.isLoading.collect { loading ->


                if (loading) {


                    buttonNext.isEnabled = false
                    buttonNext.text = "Aguarde..."


                } else {


                    buttonNext.isEnabled = true


                    if (viewPager.currentItem == 3) {


                        buttonNext.text = "Concluir"


                    }


                }


            }


        }


        // Atualizamos o observador para ler o SUCESSO_VERIFICACAO :
        lifecycleScope.launch {


            viewModel.registrationState.collect { result ->


                if (result == "SUCESSO_VERIFICACAO") {


                    android.widget.Toast.makeText(this@RegisterActivity, "Conta criada! Verifique seu e-mail antes de fazer o login.", android.widget.Toast.LENGTH_LONG).show()
                    finish()


                } else if (result != null) {


                    android.widget.Toast.makeText(this@RegisterActivity, result, android.widget.Toast.LENGTH_LONG).show()


                }


            }


        }


        // Configurando o clique do Botão de Avançar ou Concluir :
        buttonNext.setOnClickListener {


            val currentItem = viewPager.currentItem


            if (currentItem < 3) {


                viewPager.setCurrentItem(currentItem + 1, true)


            } else if (currentItem == 3) {


                viewModel.createAccount()


            }


        }


        // Configurando o clique do botão de Voltar :
        buttonBack.setOnClickListener {


            val currentItem = viewPager.currentItem


            if (currentItem > 0) {


                viewPager.setCurrentItem(currentItem - 1, true)


            }


        }


    }


    // Controla o estado de clique e as cores do botão dinamicamente :
    private fun setNextButtonState(buttonNext: MaterialButton, isValid: Boolean) {


        buttonNext.isEnabled = isValid


        if (isValid) {


            buttonNext.setBackgroundResource(R.drawable.drawable_background_button_bage_10_dp)
            buttonNext.backgroundTintList = getColorStateList(R.color.bage_neutral)
            buttonNext.setTextColor(getColor(R.color.gray_dark))


        } else {


            buttonNext.setBackgroundResource(R.drawable.drawable_background_button_gray_deep_10_dp)
            buttonNext.backgroundTintList = null
            buttonNext.setTextColor(getColor(R.color.gray_dark))


        }


    }


}