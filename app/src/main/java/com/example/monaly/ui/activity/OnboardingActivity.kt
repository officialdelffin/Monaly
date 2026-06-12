package com.example.monaly.ui.activity


// Importações :
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.monaly.R
import com.example.monaly.ui.fragment.onboarding.OnboardingFragment
import com.example.monaly.viewmodel.OnboardingViewModel


// Classe responsavel por fazer o gerenciamento e a troca dos fragments do onboarding
class OnboardingActivity : AppCompatActivity() {


    // Atributos :
    var contentIndex = 0
    var buttonBack: AppCompatButton? = null
    var buttonNext: AppCompatButton? = null
    var fragment: OnboardingFragment? = null


    // Intancias :
    val onboardingInformation = OnboardingViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {


        // Vinculando a Activity Onboarding :
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)


        // Chamando a função que inicializa o fragment e armazenando em uma variavel global :
        val fragmentGlobal = setupInitialFragment()


        // Fazendo a substituição do fragment dentro do container :
        supportFragmentManager.beginTransaction()
            .replace(R.id.containerOnboarding, fragmentGlobal)
            .commit()


        // Capturando o buttonNext e armazenando em uma variavel:
        buttonNext = findViewById(R.id.buttonNext)


        // Definindo o que o buttonBack faz :
        buttonBack?.setOnClickListener {


            if (contentIndex > 0) {


                contentIndex--


            }


            if (contentIndex == onboardingInformation.OnboardingInformation.size - 1) {


                buttonNext?.isEnabled = false


            }


            if (contentIndex == 0) {


                buttonBack?.isEnabled = false


            }


        }


        // Definindo o que o buttonNext faz :
        buttonNext?.setOnClickListener {


            // Soma mais um na variavel que controla a troca dos fragments do Onboarding :
            contentIndex++


            // Se a variavel tiver de controle de fragment for menor do que o numero de paginas que o onboarind tem ele permite uma ação :
            if (contentIndex < onboardingInformation.OnboardingInformation.size) {


                // Cria uma constante que chama a função que cria o fragment :
                val nextFragment = setupInitialFragment()


                // É aqui onde o gerenciamento realmente acontece do fragment onde o supportFragmentManager faz a troca dos dados :
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerOnboarding, nextFragment)
                    .commit()


            }


            if (contentIndex == onboardingInformation.OnboardingInformation.size - 1) {


                buttonBack?.isEnabled = false


            }


            if (contentIndex == 1) {


                buttonBack?.isEnabled = true


            }


            // Caso o número da variavel que controla a troca de activity for maior que o numero de paginas que tem, ele finaliza ( Isso é temporario ) :
            else {


                // Finalizando ( Isso tambem é temporário ) :
                finish()


            }


        }


    }


    // Esse trecho inicializa o fragmento com os dados corretos e o devolve para que a Activity possa usá-lo com o supportFragmentManager:
    fun setupInitialFragment(): OnboardingFragment {


        //Criando um novo fragment com o new fragment :
        val newFragment =
            OnboardingFragment.createNewFragment(onboardingInformation.OnboardingInformation[contentIndex])


        fragment = newFragment


        return newFragment


    }


}