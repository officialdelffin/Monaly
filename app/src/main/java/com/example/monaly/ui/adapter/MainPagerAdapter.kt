package com.example.monaly.ui.adapter


// Immportações :
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.monaly.ui.fragment.navegation.AlbumsFragment
import com.example.monaly.ui.fragment.navegation.GalleryFragment
import com.example.monaly.ui.fragment.navegation.HomeFragment
import com.example.monaly.ui.fragment.navegation.ProfileFragment


// Adaptador Global que instancia as 4 abas principais de forma segura :
class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {


    // Temos 4 abas no nosso Button Navigation :
    override fun getItemCount(): Int = 4


    // O sistema cria a aba com base na posição do deslize :
    override fun createFragment(position: Int): Fragment {


        return when (position) {


            0 -> HomeFragment()
            1 -> GalleryFragment()
            2 -> AlbumsFragment()
            3 -> ProfileFragment()
            else -> HomeFragment() // Fallback de segurança :


        }


    }


}