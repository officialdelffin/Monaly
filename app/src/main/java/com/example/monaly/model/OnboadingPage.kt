
// Pacote :
package com.example.monaly.model


// Importações :
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


// Essa é a classe que define o molde os conteudos que vão ter dentro das páginas do onboarding, sendo elas : Titulo, Imagem, Descrição :
data class OnboadingPage(


    // Atributos das paginas de onboarding :
    @StringRes val title : Int,
    @StringRes val description : Int,
    @DrawableRes val image : Int


)
