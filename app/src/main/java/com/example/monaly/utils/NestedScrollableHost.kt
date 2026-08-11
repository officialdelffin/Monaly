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


// Classe de segurança para resolver a colisão de toques em ViewPagers aninhados :
class NestedScrollableHost : FrameLayout {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    private var touchSlop = 0
    private var initialX = 0f
    private var initialY = 0f


    // Encontra o ViewPager2 pai (Trilho Global) :
    private val parentViewPager: ViewPager2?


        get() {


            var v: View? = parent as? View


            while (v != null && v !is ViewPager2) {


                v = v.parent as? View

            }


            return v as? ViewPager2


        }


    // Encontra o ViewPager2 filho (Trilho do Home) :
    private val childViewPager: ViewPager2?


        get() {


            return (0 until childCount)


                .map { getChildAt(it) }
                .firstOrNull { it is ViewPager2 } as? ViewPager2


        }


    init {


        touchSlop = ViewConfiguration.get(context).scaledTouchSlop


    }


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


    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {


        handleInterceptTouchEvent(e)
        return super.onInterceptTouchEvent(e)


    }


    // O motor inteligente que decide quem fica com o toque do dedo :
    private fun handleInterceptTouchEvent(e: MotionEvent) {


        val orientation = parentViewPager?.orientation ?: return
        val canChildScroll = canChildScroll(orientation, -1f) || canChildScroll(orientation, 1f)


        if (!canChildScroll) return


        if (e.action == MotionEvent.ACTION_DOWN) {


            initialX = e.x
            initialY = e.y
            parent.requestDisallowInterceptTouchEvent(true)

        }


        else if (e.action == MotionEvent.ACTION_MOVE) {


            val dx = e.x - initialX
            val dy = e.y - initialY
            val isVpHorizontal = orientation == ViewPager2.ORIENTATION_HORIZONTAL
            val scaledDx = dx.absoluteValue * if (isVpHorizontal) .5f else 1f
            val scaledDy = dy.absoluteValue * if (isVpHorizontal) 1f else .5f


            if (scaledDx > touchSlop || scaledDy > touchSlop) {


                if (isVpHorizontal == (scaledDy > scaledDx)) {


                    parent.requestDisallowInterceptTouchEvent(false)


                }


                else {


                    val canScroll = canChildScroll(orientation, if (isVpHorizontal) dx else dy)
                    parent.requestDisallowInterceptTouchEvent(canScroll)


                }


            }


        }


    }


}