
// Pacotes :
package com.example.monaly.viewmodel


// Importações :
import com.example.monaly.R
import com.example.monaly.model.OnboadingPage
import com.example.monaly.ui.fragment.onboarding.OnboardingFragment


// Essa classe é responsável por generenciar as informações das telas de onboarding, como foto, titulo e descrição :
class OnboardingViewModel {


    // Atributos :
    var contentIndex = 0
    var fragment : OnboardingFragment? = null


    // Lista com os dados da apresentação :
    val OnboardingInformation = listOf(


        OnboadingPage(

            title = R.string.onboarding_label_welcome,
            description = R.string.onboarding_description_one,
            image = R.drawable.img_onboardin_one

        ),

        OnboadingPage(


            title = R.string.onboarding_memories,
            description = R.string.ondoarding_description_two,
            image = R.drawable.img_onboardin_two


        ),

        OnboadingPage(



            title = R.string.onboarding_history,
            description = R.string.onboarding_description_three,
            image = R.drawable.img_onboardin_three


        )


    )

    // Esse trecho inicializa o fragmento com os dados corretos e o devolve para que a Activity possa usá-lo com o supportFragmentManager:
    fun setupInitialFragment() : OnboardingFragment {


        val newFragment = OnboardingFragment.createNewFragment(OnboardingInformation[contentIndex])


        fragment = newFragment // Salva na sua variável local se precisar


        return newFragment    // Devolve o fragmento pronto para quem chamou a função


    }


}