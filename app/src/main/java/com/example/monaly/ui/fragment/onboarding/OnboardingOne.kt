package com.example.monaly.ui.fragment.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


        // Inflando e retornando o XML em forma de view do fragment onboarding one :
        return inflater.inflate(R.layout.fragment_onboarding_one, container, false)


    }


}