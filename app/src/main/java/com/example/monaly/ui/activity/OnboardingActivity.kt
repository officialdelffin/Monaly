package com.example.monaly.ui.activity


// Importações :
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.viewpager2.widget.ViewPager2
import com.example.monaly.R
import com.example.monaly.ui.fragment.onboarding.OnboardingAdapter
import com.example.monaly.viewmodel.OnboardingViewModel


// Classe responsável por gerenciar o ciclo de vida da tela de Onboarding e a interação com os botões de navegação :
class OnboardingActivity : AppCompatActivity() {


    // Componentes de UI :
    private lateinit var viewPager: ViewPager2
    private lateinit var buttonBack: AppCompatButton
    private lateinit var buttonNext: AppCompatButton


    // Instância da ViewModel com os dados :
    private val onboardingViewModel = OnboardingViewModel()


    // Função que inicializa a activity, infla o layout e configura os listeners de eventos da tela :
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)


        // Inicializando os componentes de visualização :
        viewPager = findViewById(R.id.viewPagerOnboarding)
        buttonNext = findViewById(R.id.buttonNext)
        buttonBack = findViewById(R.id.buttonBack)


        // Configurando o Adapter do ViewPager2 com a lista de páginas da ViewModel :
        val adapter = OnboardingAdapter(this, onboardingViewModel.OnboardingInformation)
        viewPager.adapter = adapter


        // Configurando o pré-carregamento de uma página adjacente para garantir fluidez no swipe :
        viewPager.offscreenPageLimit = 1


        // Registrando um listener para interceptar quando o usuário arrastar a tela para o lado :
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            // Função chamada automaticamente sempre que uma nova página é selecionada pelo swipe :
            override fun onPageSelected(position: Int) {


                super.onPageSelected(position)


                // Atualiza o estado visual dos botões com base na página atual :
                updateButtonsState(position)


            }


        })


        // Configura o comportamento do botão "Voltar" :
        buttonBack.setOnClickListener {


            val currentItem = viewPager.currentItem


            if (currentItem > 0) {


                // Altera a página do ViewPager2 de forma animada :
                viewPager.currentItem = currentItem - 1


            }


        }


        // Configura o comportamento do botão "Próximo" :
        buttonNext.setOnClickListener {


            val currentItem = viewPager.currentItem
            val totalItems = onboardingViewModel.OnboardingInformation.size


            if (currentItem < totalItems - 1) {


                // Avança para a próxima página do Onboarding :
                viewPager.currentItem = currentItem + 1


            } else {


                // Chegou ao fim do Onboarding - comportamento temporário mantido :
                finish()


            }


        }


    }


    // Função interna usada para habilitar, desabilitar ou ocultar os botões dependendo do índice da página atual :
    private fun updateButtonsState(position: Int) {


        if (position == 0) {


            // Se for a primeira página, esconde o botão de voltar :
            buttonBack.visibility = View.INVISIBLE
            buttonBack.isEnabled = false


        }


        else {


            // Para qualquer outra página, exibe o botão de voltar :
            buttonBack.visibility = View.VISIBLE
            buttonBack.isEnabled = true


        }


        // Caso deseje mudar o texto do último botão de "Próximo" para "Entrar", o código ficaria aqui :
        val totalItems = onboardingViewModel.OnboardingInformation.size


        if (position == totalItems - 1) {


            // Mantido mapeamento original :
            buttonNext.text = getString(R.string.onboarding_bottom_next)


        }


    }


}