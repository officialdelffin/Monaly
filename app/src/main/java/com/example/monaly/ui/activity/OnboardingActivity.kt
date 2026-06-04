package com.example.monaly.ui.activity


// Importações :
import androidx.fragment.app.FragmentActivity
import com.example.monaly.R
import com.example.monaly.ui.fragment.onboarding.OnboardingFragment
import com.example.monaly.viewmodel.OnboardingViewModel


class OnboardingActivity(private val activity: FragmentActivity) {

    // Instancia a ViewModel para controlar os dados e o índice da página atual
    private val viewModel = OnboardingViewModel()

    // Esse trecho cria e insere o primeiro fragmento na tela do container quando o aplicativo é iniciado pela primeira vez:
    fun startOnboardingFlow() {
        val initialFragment =
            OnboardingFragment.createNewFragment(viewModel.OnboardingInformation[viewModel.contentIndex])

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.containerOnboarding, initialFragment) // R.id.fragmentContainer deve ser o ID do seu ContainerView ou FrameLayout no XML
            .commit()
    }

}