
// Pacotes :
package com.example.monaly.ui.viewmodel


// Importações :
import com.example.monaly.R
import com.example.monaly.domain.model.OnboadingPage


// Essa classe é responsável por generenciar as informações das telas de onboarding, como foto, titulo e descrição :
class OnboardingViewModel {


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


}