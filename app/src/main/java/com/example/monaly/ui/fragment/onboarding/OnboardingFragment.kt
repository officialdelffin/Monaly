package com.example.monaly.ui.fragment.onboarding


// Importações :
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import coil.load
import com.example.monaly.R
import com.example.monaly.domain.model.OnboadingPage


// Classe responsável por representar uma página individual do Onboarding, inflando o layout XML e vinculando os dados visuais na interface :
class OnboardingFragment : Fragment() {


    // Chamando o onCreateView para construir a interface da tela :
    override fun onCreateView(


        // Convertendo o XML em View (Elementos graficos) :
        inflater: LayoutInflater,


        // Define onde o fragment sera usado :
        container: ViewGroup?,


        // Objeto que salva os dados caso a tela seja recriada, como mudar a orientação da tela :
        savedInstanceState: Bundle?


    ): View? {


        // Inflando o layout XML e guardamos dentro da variável view :
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)


        // Atributos :


        // Pegando e vinculando os componentes que o fragment precisa :
        val title : TextView = view.findViewById(R.id.textTitle)
        val description : TextView = view.findViewById(R.id.textDescription)
        val image : ImageView = view.findViewById(R.id.imageBackground)


        // Aqui é onde verificamos se tem dados enviados para a tela do fragment :
        arguments?.let {


            // Faz a busca e a troca com base na chave que definimos :
            val titleResId = it.getInt(KEY_TITLE_ONBOARDING)
            val descriptionResId = it.getInt(KEY_DESCRIPTION_ONBOARDING)
            title.setText(titleResId)
            description.setText(descriptionResId)


            // Substituindo o método nativo pelo Coil para carregar a imagem de forma assíncrona :
            image.load(it.getInt(KEY_IMAGE_ONBOARDING))


        }


        // Retornando a variavel view que faz com que o XML em forma de view do fragment onboarding one :
        return view


    }


    // Definições de chaves :


    // Criando as chaves de acesso que trafegam no aplicativo :
    companion object {


        // Criando as chaves :
        const val KEY_TITLE_ONBOARDING = "title"
        const val KEY_DESCRIPTION_ONBOARDING = "description"
        const val KEY_IMAGE_ONBOARDING = "image"


        // Função responsável por criar os fragments e preencher os dados vinculado aos elementos do fragment :
        fun createNewFragment (dataPageP : OnboadingPage) : OnboardingFragment{


            // Pegando os dados da OnboardingPage para criar o fragment :
            val dataPage = Bundle().apply {


                putInt(KEY_TITLE_ONBOARDING, dataPageP.title)
                putInt(KEY_DESCRIPTION_ONBOARDING, dataPageP.description)
                putInt(KEY_IMAGE_ONBOARDING, dataPageP.image)


            }


            val fragment = OnboardingFragment()


            fragment.arguments = dataPage


            return fragment


        }


    }


}