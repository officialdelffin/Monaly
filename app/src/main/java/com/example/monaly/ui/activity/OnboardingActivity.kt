package com.example.monaly.ui.activity


// Importações :
import androidx.fragment.app.FragmentActivity
import com.example.monaly.ui.fragment.onboarding.OnboardingFragment
import com.example.monaly.viewmodel.OnboardingViewModel


class OnboardingActivity(private val activity: FragmentActivity) {


    // Atributos :

    var contentIndex = 0
    var fragment : OnboardingFragment? = null


    // Intancias :

    val onboardingInformation = OnboardingViewModel()


    // Esse trecho inicializa o fragmento com os dados corretos e o devolve para que a Activity possa usá-lo com o supportFragmentManager:
    fun setupInitialFragment() : OnboardingFragment {


        val newFragment = OnboardingFragment.createNewFragment(onboardingInformation.OnboardingInformation[contentIndex])


        fragment = newFragment // Salva na sua variável local se precisar


        return newFragment    // Devolve o fragmento pronto para quem chamou a função


    }

}