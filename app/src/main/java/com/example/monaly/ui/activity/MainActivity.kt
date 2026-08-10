package com.example.monaly.ui.activity


// Importações :
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.monaly.R


// Classe principal de execução :
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->


            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }


        // Encontrando o nosso NavHostFragment pelo ID :
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as androidx.navigation.fragment.NavHostFragment


        // Extraíndo o controlador de navegação interno dele :
        val navController = navHostFragment.navController


        // 1° Mapeando os nossos novos botões customizados da tela :
        val navHome = findViewById<ImageView>(R.id.navHome)
        val navGallery = findViewById<ImageView>(R.id.navGallery)
        val navAlbums = findViewById<ImageView>(R.id.navAlbums)
        val navProfile = findViewById<ImageView>(R.id.navProfile)


        // 2° Função interna para resetar todos os ícones para o estado desativado :
        fun resetAllNavItems() {


            // Volta a posição para o eixo 0 no centro :
            navHome.animate().translationY(0f).setDuration(200).start()
            navGallery.animate().translationY(0f).setDuration(200).start()
            navAlbums.animate().translationY(0f).setDuration(200).start()
            navProfile.animate().translationY(0f).setDuration(200).start()


            // Devolve a imagem normal cinza para todos :
            navHome.setImageResource(R.drawable.ic_home_bage_neutral)
            navGallery.setImageResource(R.drawable.ic_gallery_bage_neutral)
            navAlbums.setImageResource(R.drawable.ic_albums_bage_neutral)
            navProfile.setImageResource(R.drawable.ic_profile_bage_neutral)


        }


        // 3° Função que executa a animação, a troca de imagem e a navegação :
        fun selectTab(selectedView: ImageView, destinationId: Int, activeDrawable: Int) {


            // Abaixa todos e volta para as imagens cinzas :
            resetAllNavItems()


            // Levanta apenas o selecionado (ajuste o -15f se quiser que suba mais ou menos) :
            selectedView.animate().translationY(-15f).setDuration(200).start()


            // Aplica a imagem com cor destacada :
            selectedView.setImageResource(activeDrawable)


            // Manda o NavController trocar a tela (se já não estivermos nela) :
            if (navController.currentDestination?.id != destinationId) {


                navController.navigate(destinationId)


            }


        }


        // 4° Configurando os cliques para cada aba :
        navHome.setOnClickListener {


            selectTab(navHome, R.id.homeFragment, R.drawable.ic_home_gray_medium)


        }


        navGallery.setOnClickListener {


            selectTab(navGallery, R.id.galleryFragment, R.drawable.ic_gallery_gray_medium)


        }


        navAlbums.setOnClickListener {


            selectTab(navAlbums, R.id.albumsFragment, R.drawable.ic_albums_gray_medium)


        }


        navProfile.setOnClickListener {


            selectTab(navProfile, R.id.profileFragment, R.drawable.ic_profile_gray_medium)


        }


        // 5° Aciona a aba Início por padrão quando o app abrir :
        selectTab(navHome, R.id.homeFragment, R.drawable.ic_home_gray_medium)


    }


}