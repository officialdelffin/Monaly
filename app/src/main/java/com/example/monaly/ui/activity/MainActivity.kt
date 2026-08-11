package com.example.monaly.ui.activity


// Importações :
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.monaly.R
import com.example.monaly.ui.adapter.MainPagerAdapter


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


        // Mapeando o Trilho Global e os botões da barra :
        val viewPagerMain = findViewById<ViewPager2>(R.id.viewPagerMain)
        val navHome = findViewById<ImageView>(R.id.navHome)
        val navGallery = findViewById<ImageView>(R.id.navGallery)
        val navAlbums = findViewById<ImageView>(R.id.navAlbums)
        val navProfile = findViewById<ImageView>(R.id.navProfile)


        // Conectando o Adaptador ao Trilho Global :
        viewPagerMain.adapter = MainPagerAdapter(this)


        // Função interna para resetar todos os ícones para o estado desativado :
        fun resetAllNavItems() {


            navHome.animate().translationY(0f).setDuration(200).start()
            navGallery.animate().translationY(0f).setDuration(200).start()
            navAlbums.animate().translationY(0f).setDuration(200).start()
            navProfile.animate().translationY(0f).setDuration(200).start()


            navHome.setImageResource(R.drawable.ic_home_bage_neutral)
            navGallery.setImageResource(R.drawable.ic_gallery_bage_neutral)
            navAlbums.setImageResource(R.drawable.ic_albums_bage_neutral)
            navProfile.setImageResource(R.drawable.ic_profile_bage_neutral)


        }


        // Função para atualizar o visual da barra de acordo com a aba selecionada :
        fun updateBottomNavUI(position: Int) {


            resetAllNavItems()


            when (position) {


                0 -> {

                    navHome.animate().translationY(-15f).setDuration(200).start()
                    navHome.setImageResource(R.drawable.ic_home_gray_medium)

                }


                1 -> {

                    navGallery.animate().translationY(-15f).setDuration(200).start()
                    navGallery.setImageResource(R.drawable.ic_gallery_gray_medium)

                }


                2 -> {

                    navAlbums.animate().translationY(-15f).setDuration(200).start()
                    navAlbums.setImageResource(R.drawable.ic_albums_gray_medium)

                }


                3 -> {

                    navProfile.animate().translationY(-15f).setDuration(200).start()
                    navProfile.setImageResource(R.drawable.ic_profile_gray_medium)

                }



            }


        }


        // Sincronizando o arrasto do dedo com a barra inferior e definindo que sempre que o ViewPager confirmar que a tela mudou, nós atualizamos o visual da barra :
        viewPagerMain.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {


            override fun onPageSelected(position: Int) {


                super.onPageSelected(position)
                updateBottomNavUI(position)


            }


        })


        // Configurando os cliques que o botão apenas avisa o ViewPager para trocar de tela e também, o parâmetro true faz a tela deslizar suavemente até a nova aba :
        navHome.setOnClickListener {


            viewPagerMain.setCurrentItem(0, true)


        }


        navGallery.setOnClickListener {


            viewPagerMain.setCurrentItem(1, true)


        }


        navAlbums.setOnClickListener {


            viewPagerMain.setCurrentItem(2, true)


        }


        navProfile.setOnClickListener {


            viewPagerMain.setCurrentItem(3, true)


        }


    }


}