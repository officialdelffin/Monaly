package com.example.monaly.ui.viewmodel


// Importações :
import com.example.monaly.domain.model.OnboadingPage
import com.example.monaly.data.repository.OnboardingRepositoryImplementation
import com.example.monaly.domain.repository.OnboardingRepository
import androidx.lifecycle.ViewModel
import com.example.monaly.core.network.ConnectivityNetworkMonitor
import com.example.monaly.core.network.NetworkMonitor

// O ViewModel recebe o repositório como uma dependência para funcionar :
class OnboardingViewModel(


    // Instanciando temporariamente a implementação manual (pagaremos essa dívida técnica com Injeção de Dependência depois) :
    private val repository: OnboardingRepository = OnboardingRepositoryImplementation(),


    private val networkMonitor: NetworkMonitor


) : ViewModel() {


    // A variável agora chama a função do repositório em vez de fabricar a lista :
    val OnboardingInformation: List<OnboadingPage> = repository.getPages()


}