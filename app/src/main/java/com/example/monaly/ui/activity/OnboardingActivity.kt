package com.example.monaly.ui.activity


// Importações :
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.monaly.R
import com.example.monaly.core.network.ConnectivityNetworkMonitor
import com.example.monaly.ui.fragment.onboarding.OnboardingAdapter
import com.example.monaly.ui.viewmodel.OnboardingViewModel
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch


// Classe responsável por gerenciar o ciclo de vida da tela de Onboarding e a interação com os botões de navegação :
class OnboardingActivity : AppCompatActivity() {


    // Componentes de UI :
    private lateinit var viewPager: ViewPager2
    private lateinit var buttonBack: AppCompatButton
    private lateinit var buttonNext: MaterialButton


    // Avisamos ao Kotlin que o ViewModel será inicializado depois, usando a Fábrica dentro do onCreate :
    private lateinit var onboardingViewModel: OnboardingViewModel


    // Função que inicializa a activity, infla o layout e configura os listeners de eventos da tela :
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)


        // Instanciamos o nosso monitor passando o Context this representa a própria Activity :
        val networkMonitor = ConnectivityNetworkMonitor(this)


        // Criamos uma fábrica para ensinar o Android a construir o OnboardingViewModel :
        val factory = object : ViewModelProvider.Factory {


            // Sobrescrevemos o método de criação padrão :
            override fun <T : ViewModel> create(modelClass: Class<T>): T {


                // Retornamos o nosso ViewModel injetando o monitor recém-criado :
                @Suppress("UNCHECKED_CAST")
                return OnboardingViewModel(networkMonitor = networkMonitor) as T


            }


        }


        // Finalmente, pedimos ao Android para nos dar o ViewModel usando a nossa fábrica :
        onboardingViewModel = ViewModelProvider(this, factory)[OnboardingViewModel::class.java]


        // Inicializando os componentes de visualização :
        viewPager = findViewById(R.id.viewPagerOnboarding)
        buttonNext = findViewById(R.id.buttonNext)
        buttonBack = findViewById(R.id.buttonBack)


        // Mantém o fluxo de internet sempre ativo e observando as mudanças reais de rede :
        lifecycleScope.launch {


            onboardingViewModel.isOnline.collect { status -> }


        }


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


                // Chegou ao fim do Onboarding, verificamos a internet no fluxo de estado :
                val hasInternet = onboardingViewModel.isOnline.value


                if (hasInternet) {


                    // Se tem internet, criamos a intenção de navegar para a tela de Login :
                    val intent = Intent(this, LoginActivity::class.java)


                    // Iniciamos a nova tela :
                    startActivity(intent)


                    // Encerramos a tela de onboarding para o usuário não voltar com o botão de voltar do sistema :
                    finish()


                } else {


                    // Se não tem internet, exibimos uma notificação bloqueando o acesso :
                    Toast.makeText(this, "Sem conexão com a internet. Verifique sua rede e tente novamente.", Toast.LENGTH_LONG).show()


                }


            }


        }


    }


    // Função interna usada para habilitar, desabilitar ou ocultar os botões dependendo do índice da página atual e também faz a troca dos status do buttom :
    private fun updateButtonsState(position: Int) {


        // Se for a primeira página, esconde o botão de voltar :
        if (position == 0) {


            buttonBack.visibility = View.INVISIBLE
            buttonBack.isEnabled = false


        }


        // Se não for a primeira página, habilita o botão novamente :
        if (position != 0) {


            buttonBack.visibility = View.VISIBLE
            buttonBack.isEnabled = true


        }


        // Se for a 3 página ele muda as cores do button e muda o text :
        if (position == 2) {


            // Definindo novos atributos do buttonNext :
            val newTextEnter : String = getString(R.string.onboarding_bottom_login_in)
            val newTextColorEnter : Int = ContextCompat.getColor(this, R.color.gray_dark)
            val newColorBage : Int = ContextCompat.getColor(this, R.color.bage_neutral)
            val newArrowIcon : Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right_gray_dark)


            // Se for a terceira página, o botão vai alterar a cor para bage e o text vai mudar para a string entrar :
            buttonNext.text = newTextEnter
            buttonNext.backgroundTintList = ColorStateList.valueOf(newColorBage)
            buttonNext.setTextColor(newTextColorEnter)


            // Usamos a propriedade nativa do MaterialButton para injetar a imagem corretamente :
            buttonNext.icon = newArrowIcon


        }


        // Definindo cor e text padrão para sempre voltarem as cores padrões quando sair da 3° tela do Onboarding :
        if (position != 2) {


            // Definindo as cores padrões do buttonNext :
            val defaultTextButtonNext : String = getString(R.string.onboarding_bottom_next)
            val defaultTextColorButtonNext : Int = ContextCompat.getColor(this, R.color.bage_neutral)
            val defaultColorButtonNext : Int = ContextCompat.getColor(this, R.color.gray_dark)
            val defaultArrowIcon : Drawable? = ContextCompat.getDrawable(this, R.drawable.ic_arrow_right_bage_neutral)


            // Restaura o texto e as cores originais :
            buttonNext.text = defaultTextButtonNext
            buttonNext.backgroundTintList = ColorStateList.valueOf(defaultColorButtonNext)
            buttonNext.setTextColor(defaultTextColorButtonNext)


            // Restaura o ícone original de forma nativa :
            buttonNext.icon = defaultArrowIcon


        }


    }


}