package com.vladiknt.piceditnt.filters

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.set

object RainbowFilter {
    private lateinit var image: Bitmap

    fun make(input: Bitmap): Bitmap {
        image = input.copy(input.config, true)
        input.recycle()
        render()
        return image
    }

    private fun render() {
        val heightStrip = image.height / 6
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val current = image[i, j]
                val value = when {
                        j <= heightStrip -> Color.argb(255, 245, 66, 66)
                        j <= heightStrip * 2 -> Color.argb(255, 245, 138, 66)
                        j <= heightStrip * 3 -> Color.argb(255, 245, 221, 66)
                        j <= heightStrip * 4 -> Color.argb(255, 81, 245, 66)
                        j <= heightStrip * 5 -> Color.argb(255, 66, 215, 245)
                        else -> Color.argb(255, 144, 66, 245)
                    }
                val color = Color.argb(
                    Color.alpha(current),
                    (Color.red(value) * 0.3 + Color.red(current) * 0.7).toInt(),
                    (Color.green(value) * 0.3 + Color.green(current) * 0.7).toInt(),
                    (Color.blue(value) * 0.3 + Color.blue(current) * 0.7).toInt()
                )
                image[i, j] = color
            }
        }
    }
}