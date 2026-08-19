package com.example.monaly.ui.adapter


//Importações
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.monaly.ui.fragment.navegation.CameraFragment
import com.example.monaly.ui.fragment.navegation.FeedFragment


// Adapter responsável por instanciar as telas de forma isolada e segura na memória :
class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {


    // Definimos que teremos exatamente 2 telas neste trilho :
    override fun getItemCount(): Int = 2


    // O sistema decide qual Fragment criar baseado na posição do deslize (0 = Esquerda/Feed, 1 = Direita/Câmera) :
    override fun createFragment(position: Int): Fragment {


        return when (position) {


            // Posição 0 (Esquerda): Agora a Câmera fica no lado esquerdo :
            0 -> CameraFragment()


            // Posição 1 (Direita): O Feed fica no centro/direita :
            1 -> FeedFragment()


            // Fallback de segurança para evitar falhas :
            else -> FeedFragment()


        }


    }


}