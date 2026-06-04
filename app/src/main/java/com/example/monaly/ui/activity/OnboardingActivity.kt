package com.example.monaly.ui.activity


// Importações :
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.monaly.R
import com.example.monaly.ui.fragment.onboarding.OnboardingFragment
import com.example.monaly.viewmodel.OnboardingViewModel


class OnboardingActivity : AppCompatActivity() {


    // Atributos :

    var contentIndex = 0
    var fragment : OnboardingFragment? = null


    // Intancias :

    val onboardingInformation = OnboardingViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {


        // Vinculando a Activity Onboarding :
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)


        supportFragmentManager.beginTransaction()
            .replace(R.id.containerOnboarding,fragment)
            .commit()


    }


    // Esse trecho inicializa o fragmento com os dados corretos e o devolve para que a Activity possa usá-lo com o supportFragmentManager:
    fun setupInitialFragment() : OnboardingFragment {


        //Criando um novo fragment com o new fragment :
        val newFragment = OnboardingFragment.createNewFragment(onboardingInformation.OnboardingInformation[contentIndex])


        fragment = newFragment
        return newFragment


    }


}