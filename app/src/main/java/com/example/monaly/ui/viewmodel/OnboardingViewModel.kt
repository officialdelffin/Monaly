package com.example.monaly.ui.viewmodel


// Importações :
import com.example.monaly.domain.model.OnboadingPage
import com.example.monaly.data.repository.OnboardingRepositoryImplementation
import com.example.monaly.domain.repository.OnboardingRepository


// O ViewModel recebe o repositório como uma dependência para funcionar :
class OnboardingViewModel(


    // Instanciando temporariamente a implementação manual (pagaremos essa dívida técnica com Injeção de Dependência depois) :
    private val repository: OnboardingRepository = OnboardingRepositoryImplementation()


) {


    // A variável agora chama a função do repositório em vez de fabricar a lista :
    val OnboardingInformation: List<OnboadingPage> = repository.getPages()


}