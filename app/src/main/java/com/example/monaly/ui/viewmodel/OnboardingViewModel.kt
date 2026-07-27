package com.example.monaly.ui.viewmodel


// Importações :
import com.example.monaly.domain.model.OnboadingPage
import com.example.monaly.data.repository.OnboardingRepositoryImplementation
import com.example.monaly.domain.repository.OnboardingRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import com.example.monaly.core.network.NetworkMonitor

// O ViewModel recebe o repositório como uma dependência para funcionar :
class OnboardingViewModel(


    // Instanciando temporariamente a implementação manual (pagaremos essa dívida técnica com Injeção de Dependência depois) :
    private val repository: OnboardingRepository = OnboardingRepositoryImplementation(),


    private val networkMonitor: NetworkMonitor


) : ViewModel() {


    // A variável agora chama a função do repositório em vez de fabricar a lista :
    val OnboardingInformation: List<OnboadingPage> = repository.getPages()


    // Criando a variável de estado que a Activity vai observar depois :
    val isOnline: StateFlow<Boolean> = networkMonitor.isOnline


        .stateIn(


            // O escopo seguro do ViewModel que garante que o fluxo pare se o app fechar :
            scope = viewModelScope,


            // Regra de compartilhamento (mantém ativo por 5 segundos após a tela sumir para evitar reinícios desnecessários) :
            started = SharingStarted.WhileSubscribed(5000),


            // Valor inicial antes do monitor terminar a primeira checagem :
            initialValue = false


        )


}