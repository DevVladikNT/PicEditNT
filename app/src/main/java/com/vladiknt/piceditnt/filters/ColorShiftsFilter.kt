package com.vladiknt.piceditnt.filters

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.set
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

object ColorShiftsFilter {
    private lateinit var result: Bitmap

    fun make(input: Bitmap): Bitmap {
        result = input.copy(input.config, true)
        render()
        return result
    }

    private fun render() {
        repeat(5) {
            for (i in 1 until result.width) {
                for (j in 1 until result.height) {
                    val pixel = result[i, j]
                    val pixel1 = result[i - 1, j]
                    val pixel2 = result[i, j - 1]
                    val pixel3 = result[i - 1, j - 1]
                    val r = (Color.red(pixel) * 0.5 + Color.red(pixel1) * 0.5).toInt()
                    val g = (Color.green(pixel) * 0.5 + Color.green(pixel2) * 0.5).toInt()
                    val b = (Color.blue(pixel) * 0.5 + Color.blue(pixel3) * 0.5).toInt()
                    val color = Color.argb(Color.alpha(pixel), r, g, b)
                    result[i, j] = color
                }
            }
        }
    }
}