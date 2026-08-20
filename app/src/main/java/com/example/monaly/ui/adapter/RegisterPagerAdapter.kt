package com.example.monaly.ui.adapter


// Importações :
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.monaly.ui.fragment.register.RegisterEmailFragment
import com.example.monaly.ui.fragment.register.RegisterPasswordFragment
import com.example.monaly.ui.fragment.register.RegisterProfileFragment
import com.example.monaly.ui.fragment.RegisterSummaryFragment


// O Adaptador que gerencia a troca de telas do cadastro :
class RegisterPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {


    // Agora temos 4 etapas no total :
    override fun getItemCount(): Int {


        return 4


    }


    // Retorna o Fragment correto para cada posição, incluindo o Resumo na posição 3 :
    override fun createFragment(position: Int): Fragment {


        return when (position) {


            0 -> RegisterEmailFragment()
            1 -> RegisterProfileFragment()
            2 -> RegisterPasswordFragment()
            3 -> RegisterSummaryFragment()


            else -> throw IllegalArgumentException("Posição inválida no ViewPager")


        }


    }


}