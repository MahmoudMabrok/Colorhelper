package tools.mahmoudmabrok.colorlearner.feature

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Path
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jem.rubberpicker.RubberSeekBar
import kotlinx.android.synthetic.main.main.*
import tools.mahmoudmabrok.colorlearner.R
import tools.mahmoudmabrok.colorlearner.util.ColorHelper

class MainActivity : AppCompatActivity() {

    val mTag = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        initListnerForSeekValues()

        btnMix.setOnClickListener {
            /**
             * Two actions are done
             * 1 move colors into center of result
             * 2 change color of result view
             */
            //    moveViews()
            animateResultView()
        }
    }

    /**
     * add new color that results from 3 components (R.G.B)
     */
    private fun animateResultView() {
        tvresult.animateColor(
            ColorHelper.colorWith(
                red = redBar.getCurrentValue(),
                green = greenBar.getCurrentValue(),
                blue = blueBar.getCurrentValue()
            )
        )
    }

    /**
     * move color components into center of result view
     */
    private fun moveViews() {
        tvRed.animateToCenter(tvresult)
        tvGreen.animateToCenter(tvresult)
        tvBlue.animateToCenter(tvresult)

    }

    private fun initListnerForSeekValues() {
        redBar.setOnRubberSeekBarChangeListener(object :
            RubberSeekBar.OnRubberSeekBarChangeListener {
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                Log.d(mTag, value.toString())
                tvRed.animateColor(ColorHelper.colorWith(red = value))
            }

            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })

        greenBar.setOnRubberSeekBarChangeListener(object :
            RubberSeekBar.OnRubberSeekBarChangeListener {
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                Log.d(mTag, value.toString())
                tvGreen.animateColor(ColorHelper.colorWith(green = value))
            }

            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })

        blueBar.setOnRubberSeekBarChangeListener(object :
            RubberSeekBar.OnRubberSeekBarChangeListener {
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                Log.d(mTag, value.toString())
                tvBlue.animateColor(ColorHelper.colorWith(blue = value))
            }

            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })
    }

}


/**
 * animate changing of result view
 */
fun View.animateColor(toColor: Int) {
    /**
     * color animation fromm current color of view into result color
     */
    val colorAnimation =
        ValueAnimator.ofObject(ArgbEvaluator(), (this.background as? ColorDrawable)?.color, toColor)
    colorAnimation.duration = 100L
    colorAnimation.addUpdateListener { animator -> this.setBackgroundColor(animator.animatedValue as Int) }
    colorAnimation.start()
}

/**
 * animate moving a view to center of other view
 */
fun View.animateToCenter(targetView: View) {
    val animator = ObjectAnimator.ofFloat(this, View.X, View.Y, Path().apply {
        lineTo(targetView.x, targetView.y)
    }).apply {
        duration = 700
        start()
    }
}
