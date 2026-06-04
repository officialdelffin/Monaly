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
    var buttonNext : AppCompatButton? = null
    var fragment : OnboardingFragment? = null


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
            .replace(R.id.containerOnboarding,fragmentGlobal)
            .commit()


        // Capturando o buttonNext e armazenando em uma variavel:
        buttonNext = findViewById(R.id.buttonNext)


    }


    // Esse trecho inicializa o fragmento com os dados corretos e o devolve para que a Activity possa usá-lo com o supportFragmentManager:
    fun setupInitialFragment() : OnboardingFragment {


        //Criando um novo fragment com o new fragment :
        val newFragment = OnboardingFragment.createNewFragment(onboardingInformation.OnboardingInformation[contentIndex])


        fragment = newFragment
        return newFragment


    }


}