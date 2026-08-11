package com.example.monaly.utils


// Importações :
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.absoluteValue
import kotlin.math.sign


// Classe projetada para atuar como um mediador e resolver a colisão de toques entre componentes ViewPager2 aninhados :
class NestedScrollableHost : FrameLayout {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    // Variável que armazena a distância mínima que o dedo deve percorrer antes que o sistema reconheça como um arrasto :
    private var touchSlop = 0


    // Variáveis para armazenar as coordenadas exatas do momento em que a tela é tocada :
    private var initialX = 0f
    private var initialY = 0f


    // Realiza a busca na hierarquia de visualizações para encontrar o ViewPager2 principal sendo o componente pai :
    private val parentViewPager: ViewPager2?


        get() {


            var v: View? = parent as? View
            while (v != null && v !is ViewPager2) {


                v = v.parent as? View


            }


            return v as? ViewPager2


        }


    // Inspeciona os elementos internos encapsulados por esta classe para identificar o ViewPager2 secundário sendo o componente filho :
    private val childViewPager: ViewPager2?


        get() {


            return (0 until childCount)


                .map { getChildAt(it) }
                .firstOrNull { it is ViewPager2 } as? ViewPager2


        }


    // Bloco de inicialização que define a sensibilidade de toque padrão do dispositivo atual :
    init {


        touchSlop = ViewConfiguration.get(context).scaledTouchSlop


    }


    // Verifica se o componente filho tem conteúdo suficiente e permissão para rolar na direção que o usuário está puxando :
    private fun canChildScroll(orientation: Int, delta: Float): Boolean {


        val direction = -delta.sign.toInt()
        val child = childViewPager ?: return false


        return if (orientation == 0) {


            child.canScrollHorizontally(direction)


        }


        else {


            child.canScrollVertically(direction)


        }


    }


    // Metodo acionado pelo sistema operacional assim que um toque é detectado na tela, antes de ser passado para as visualizações :
    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {


        handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)


    }


    // Lógica central e matemática que analisa a física do movimento para decidir qual componente deve rolar a tela :
    private fun handleInterceptTouchEvent(e: MotionEvent) {


        // Verifica a orientação do componente pai para saber em qual eixo o arrasto deve atuar :
        val orientation = parentViewPager?.orientation ?: return


        // Confirma se o componente filho pode rolar para a esquerda ou para a direita :
        val canChildScroll = canChildScroll(orientation, -1f) || canChildScroll(orientation, 1f)


        // Se o filho não puder rolar, o processamento é interrompido e o toque é devolvido ao pai imediatamente :
        if (!canChildScroll) return


        if (e.action == MotionEvent.ACTION_DOWN) {


            // Registra as coordenadas X e Y no exato momento em que o dedo encosta na tela :
            initialX = e.x
            initialY = e.y


            // Bloqueia temporariamente o componente pai de roubar o evento de toque para que o filho possa analisar o gesto inicial :
            parent.requestDisallowInterceptTouchEvent(true)


        } else if (e.action == MotionEvent.ACTION_MOVE) {


            // Calcula a distância absoluta do movimento percorrido pelo dedo durante o arrasto :
            val dx = e.x - initialX
            val dy = e.y - initialY
            val isVpHorizontal = orientation == ViewPager2.ORIENTATION_HORIZONTAL


            // Aplica um peso multiplicador aos eixos. Isso calibra a sensibilidade para diferenciar um arrasto horizontal intencional de um deslize diagonal acidental :
            val scaledDx = dx.absoluteValue * if (isVpHorizontal) .5f else 1f
            val scaledDy = dy.absoluteValue * if (isVpHorizontal) 1f else .5f


            // Verifica se o dedo percorreu uma distância maior do que a margem de erro do sistema (touchSlop) :
            if (scaledDx > touchSlop || scaledDy > touchSlop) {


                // Se o movimento for predominantemente perpendicular à orientação da barra, sendo arrastando para cima quando a barra é horizontal, o pai retoma o controle :
                if (isVpHorizontal == (scaledDy > scaledDx)) {


                    parent.requestDisallowInterceptTouchEvent(false)


                } else {


                    // Caso o movimento seja na direção correta, verifica se o filho ainda tem tela para rolar. Se tiver, o filho mantém o bloqueio do pai e executa o arrasto :
                    val canScroll = canChildScroll(orientation, if (isVpHorizontal) dx else dy)
                    parent.requestDisallowInterceptTouchEvent(canScroll)


                }


            }


        }


    }


}