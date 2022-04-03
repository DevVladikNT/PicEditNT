package com.vladiknt.piceditnt.filters

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.set
import kotlin.math.pow
import kotlin.math.sqrt

object PixelFilter {
    private lateinit var image: Bitmap
    private val colors = arrayListOf(
            Color.argb(255, 0, 0, 0),
            Color.argb(255, 34, 32, 52),
            Color.argb(255, 69, 40, 60),
            Color.argb(255, 102, 57, 49),
            Color.argb(255, 143, 86, 59),
            Color.argb(255, 223, 113, 38),

            Color.argb(255, 217, 160, 102),
            Color.argb(255, 238, 195, 154),
            Color.argb(255, 251, 242, 54),
            Color.argb(255, 153, 229, 80),
            Color.argb(255, 105, 190, 48),
            Color.argb(255, 55, 148, 110),

            Color.argb(255, 75, 105, 47),
            Color.argb(255, 82, 75, 35),
            Color.argb(255, 50, 60, 57),
            Color.argb(255, 63, 63, 115),
            Color.argb(255, 48, 96, 130),

            Color.argb(255, 91, 110, 225),
            Color.argb(255, 99, 155, 255),
            Color.argb(255, 94, 206, 228),
            Color.argb(255, 203, 219, 252),
            Color.argb(255, 255, 255, 255),

            Color.argb(255, 155, 173, 183),
            Color.argb(255, 132, 126, 135),
            Color.argb(255, 105, 106, 106),
            Color.argb(255, 88, 86, 82),
            Color.argb(255, 118, 65, 138),

            Color.argb(255, 172, 50, 50),
            Color.argb(255, 218, 87, 99),
            Color.argb(255, 216, 122, 186),
            Color.argb(255, 142, 151, 74),
            Color.argb(255, 138, 111, 48)
    )

    fun make(input: Bitmap): Bitmap {
        image = input.copy(input.config, true)
        render()
        return image
    }

    private fun render() {
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val pixel = image[i, j]
                val red = Color.red(pixel).toDouble()
                val green = Color.green(pixel).toDouble()
                val blue = Color.blue(pixel).toDouble()
                var resultLength = 255.0 * 255 + 255 * 255 + 255 * 255
                var resultColor = Color.argb(255, 255, 255, 255)

                colors.forEach {
                    val length = (red - Color.red(it)).pow(2.0) + (green - Color.green(it)).pow(2.0) + (blue - Color.blue(it)).pow(2.0)
                    if (length < resultLength) {
                        resultLength = length
                        resultColor = it
                    }
                }

                image[i, j] = resultColor
            }
        }
        image = scaling(image)
    }

    private fun scaling(input: Bitmap): Bitmap {
        val scalingImage = Bitmap.createBitmap(input.width/2, input.height/2, Bitmap.Config.ARGB_8888)
        for (i in 0 until input.width/2) {
            for (j in 0 until input.height/2) {
                val pixel1 = input[i * 2, j * 2]
                val pixel2 = input[i * 2 + 1, j * 2]
                val pixel3 = input[i * 2, j * 2 + 1]
                val pixel4 = input[i * 2 + 1, j * 2 + 1]
                val red = (Color.red(pixel1) + Color.red(pixel2) + Color.red(pixel3) + Color.red(pixel4)) / 4.0
                val green = (Color.green(pixel1) + Color.green(pixel2) + Color.green(pixel3) + Color.green(pixel4)) / 4.0
                val blue = (Color.blue(pixel1) + Color.blue(pixel2) + Color.blue(pixel3) + Color.blue(pixel4)) / 4.0
                var resultLength = 255.0 * 255 + 255 * 255 + 255 * 255
                var resultColor = Color.argb(255, 255, 255, 255)

                colors.forEach {
                    val length = (red - Color.red(it)).pow(2.0) + (green - Color.green(it)).pow(2.0) + (blue - Color.blue(it)).pow(2.0)
                    if (length < resultLength) {
                        resultLength = length
                        resultColor = it
                    }
                }

                scalingImage[i, j] = resultColor
            }
        }

        return if (scalingImage.height > 256 && scalingImage.width > 256)
            scaling(scalingImage)
        else
            scalingImage
    }
}