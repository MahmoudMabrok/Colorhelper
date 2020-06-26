package tools.mahmoudmabrok.colorlearner.util

import android.content.Context
import android.graphics.Color


object ColorHelper {

    fun colorWith(ctx:Context,red:Int = 0 , green:Int = 0 , blue:Int = 0) = Color.rgb(red, green, blue)

}