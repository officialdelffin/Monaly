package com.example.monaly.data.repository


// Importações :
import com.example.monaly.R
import com.example.monaly.domain.model.OnboadingPage
import com.example.monaly.domain.repository.OnboardingRepository


// Classe responsável por buscar e fornecer esses dados para o aplicativo :
class OnboardingRepositoryImplementation : OnboardingRepository {


    // Sobrescrevendo o metodo getPage da interface OnboardingRepository :
    override fun getPages(): List<OnboadingPage> {


        // Retornando a list com todos os dados para fornecer pora fazer a entrega :
        return listOf(


            // Dados da pagina one :
            OnboadingPage(


                title = R.string.onboarding_label_welcome,
                description = R.string.onboarding_description_one,
                image = R.drawable.img_background_onboardin_one


            ),


            // Dados da pagina two :
            OnboadingPage(


                title = R.string.onboarding_memories,
                description = R.string.ondoarding_description_two,
                image = R.drawable.img_background_onboardin_two


            ),


            // Dados da pagina three :
            OnboadingPage(


                title = R.string.onboarding_history,
                description = R.string.onboarding_description_three,
                image = R.drawable.img_background_onboardin_three


            )


        )


    }


}