package tools.mahmoudmabrok.colorlearner.util

import android.graphics.Color


object ColorHelper {

    fun colorWith(red: Int = 0, green: Int = 0, blue: Int = 0) = Color.rgb(red, green, blue)
    fun colorToHex(color: Int) = color.toString(16).padStart(6, '0')

}