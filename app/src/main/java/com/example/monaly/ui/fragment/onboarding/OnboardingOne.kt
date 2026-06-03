package com.example.monaly.ui.fragment.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.monaly.R


class OnboardingOne : Fragment() {


    // Chamando o onCreateView para construir a interface da tela :
    override fun onCreateView(


        // Convertendo o XML em View (Elementos graficos) :
        inflater: LayoutInflater,


        // Define onde o fragment sera usado :
        container: ViewGroup?,


        // Objeto que salva os dados caso a tela seja recriada, como mudar a orientação da tela:
        savedInstanceState: Bundle?


    ): View? {


        // Inflando o layout XML e guardamos dentro da variável view :
        val view = inflater.inflate(R.layout.fragment_onboarding, container, false)


        // Atributos :

        // Pegando e vinculando os componentes que o fragment precisa :
        val title : TextView = view.findViewById(R.id.textTitle)
        val description : TextView = view.findViewById(R.id.textDescription)
        val image : ImageView = view.findViewById(R.id.imageBackground)


        // Aqui é onde verificamos se tem dados enviados para a tela do fragment, se estiver como titulo e descrição vamos fazer a substituição para que não trave o app:
        arguments?.let {


            // Faz a busca e a troca com base na chave que definimos :
            title.text = it.getString(KEY_TITLE_ONBOARDING)
            description.text = it.getString(KEY_DESCRIPTION_ONBOARDING)


        }


        // Retornando a variavel view que faz com que o XML em forma de view do fragment onboarding one :
        return view


    }


    // Definições de chaves:

    // Criando as chaves de acesso que trafegam no aplicativo
    companion object {


        // Criando as chaves :
        const val KEY_TITLE_ONBOARDING = "title"
        const val KEY_DESCRIPTION_ONBOARDING = "description"
        const val KEY_IMAGE_ONBOARDING = "image"


    }


}