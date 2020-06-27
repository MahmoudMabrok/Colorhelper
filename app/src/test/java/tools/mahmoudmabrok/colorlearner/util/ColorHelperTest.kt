package tools.mahmoudmabrok.colorlearner.util

import org.junit.Assert.assertEquals
import org.junit.Test

class ColorHelperTest {

    @Test
    fun colorToHex() {
        val input = 255
        val result = ColorHelper.colorToHex(input)
        val expected = "0000ff"

        assertEquals(expected, result)
    }
}