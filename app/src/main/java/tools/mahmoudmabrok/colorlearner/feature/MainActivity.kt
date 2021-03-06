package tools.mahmoudmabrok.colorlearner.feature

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.jem.rubberpicker.RubberSeekBar
import kotlinx.android.synthetic.main.main.*
import tools.mahmoudmabrok.colorlearner.R
import tools.mahmoudmabrok.colorlearner.util.ColorHelper


class MainActivity : AppCompatActivity() {

    val mTag = javaClass.simpleName

    val srcViews by lazy { listOf<ShapeableImageView>(tvRed, tvGreen, tvBlue) }


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
            moveViews()

        }


        addCornerToViews()


    }

    private fun addCornerToViews() {
        val radius = this.resources.getDimension(R.dimen.border_radius_hug)
        val mode = ShapeAppearanceModel()
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, radius)
            .build()

        srcViews.forEach {
            it.shapeAppearanceModel = mode
        }

        tvresult.shapeAppearanceModel = mode

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
        tvBlue.animateToCenter(tvresult) {
            animateResultView()
        }

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
fun View.animateToCenter(targetView: View, actionAtEnd: (() -> Unit)? = null) {
    Log.d("animateToCenter", "$this $targetView")

    // used tom return view to its base place
    val pvhXReverse = PropertyValuesHolder.ofFloat(View.X, this.x)
    val pvhYReverse = PropertyValuesHolder.ofFloat(View.Y, this.y)
    val animatorReverse: ObjectAnimator =
        ObjectAnimator.ofPropertyValuesHolder(this, pvhXReverse, pvhYReverse)
    animatorReverse.duration = 100

    // base animator to animate view into result view by changing Y,X
    val pvhX = PropertyValuesHolder.ofFloat(View.X, targetView.x)
    val pvhY = PropertyValuesHolder.ofFloat(View.Y, targetView.y)
    val animator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(this, pvhX, pvhY)
    animator.duration = 1200
    animator.start()

    animator.apply {
        addListener(onEnd = {
            actionAtEnd?.invoke()
            animatorReverse.start()
        })
    }
}
