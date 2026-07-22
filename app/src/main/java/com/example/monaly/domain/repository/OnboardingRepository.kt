package com.example.monaly.domain.repository


// Importações :
import com.example.monaly.domain.model.OnboadingPage


// Interface que fica responsavel por toda classe que herda ela, tera que ter a função getPage :
interface OnboardingRepository {


    // criando a função getPage que devolve uma lista de objetos da pagina do OnboardingPage :
    fun getPage() : List<OnboadingPage>


}