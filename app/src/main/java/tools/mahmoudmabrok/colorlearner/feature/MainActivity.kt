package tools.mahmoudmabrok.colorlearner.feature

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.main.*
import tools.mahmoudmabrok.colorlearner.R
import tools.mahmoudmabrok.colorlearner.util.ColorHelper

class MainActivity : AppCompatActivity() {

    var currentColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        tvresult.setBackgroundColor(ColorHelper.colorWith(this,255,255,255))
        animateColorValue(tvresult)
    }

    private fun animateColorValue(view: View,start:Int =  Color.GRAY,end:Int  =Color.CYAN) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), start, end)
        colorAnimation.duration = 1500L
        colorAnimation.addUpdateListener { animator -> view.setBackgroundColor(animator.animatedValue as Int) }
        colorAnimation.start()
    }
}
