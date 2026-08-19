package com.example.monaly.ui.adapter


// Importações :
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.monaly.ui.fragment.register.RegisterEmailFragment
import com.example.monaly.ui.fragment.register.RegisterPasswordFragment
import com.example.monaly.ui.fragment.register.RegisterProfileFragment


// Adaptador seguro para o ViewPager2 do cadastro progressivo :
class RegisterPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {


    // Por enquanto, temos 3 etapas construídas :
    override fun getItemCount(): Int = 3


    // O sistema cria as etapas de forma isolada na memória conforme a posição :
    override fun createFragment(position: Int): Fragment {


        return when (position) {


            0 -> RegisterEmailFragment()
            1 -> RegisterProfileFragment()
            2 -> RegisterPasswordFragment()
            else -> RegisterEmailFragment() // Fallback de segurança :


        }


    }


}