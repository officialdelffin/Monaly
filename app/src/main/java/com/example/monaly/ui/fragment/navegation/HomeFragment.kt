package com.example.monaly.ui.fragment.navegation


// Importações :
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.monaly.R
import com.example.monaly.ui.adapter.HomePagerAdapter


// Usando o construtor direto do Fragment para injetar o layout sem precisar de boilerplate :
class HomeFragment : Fragment(R.layout.fragment_home) {


    // Usando o onViewCreated para ter segurança de que o layout XML já foi totalmente carregado :
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)


        // Encontramos o componente de arrastar na tela :
        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerHome)


        // Instanciamos o adaptador e conectamos ao ViewPager2 :
        val adapter = HomePagerAdapter(this)
        viewPager.adapter = adapter


        // Forçamos o ViewPager a iniciar na posição 1 sendo o Feed, em vez da 0 sendo a Câmera e fazendo com que o parâmetro false desativa a animação de rolagem para que a tela mude instantaneamente nos bastidores :
        viewPager.setCurrentItem(1, false)


    }


}