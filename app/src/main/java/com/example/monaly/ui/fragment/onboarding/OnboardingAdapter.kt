package com.example.monaly.ui.fragment.onboarding


// Importações :
import com.example.monaly.model.OnboadingPage
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter



class OnboardingAdapter( activity: AppCompatActivity, private val pages: List<OnboadingPage> ) : FragmentStateAdapter(activity) {


    // Define a quantidade total de páginas no Onboarding :
    override fun getItemCount(): Int = pages.size


    // Cria o fragmento com os dados corretos baseado na posição atual :
    override fun createFragment(position: Int): Fragment {


        return OnboardingFragment.createNewFragment(pages[position])


    }


}