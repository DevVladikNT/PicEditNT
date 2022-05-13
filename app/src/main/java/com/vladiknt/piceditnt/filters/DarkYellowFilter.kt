package com.vladiknt.piceditnt.filters

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.set

object DarkYellowFilter {
    /** Поле изображение */
    private lateinit var image: Bitmap
    /** Поле матрица яркости пикселей */
    private lateinit var matrix: Array<DoubleArray>

    /**
     * Функция, работающая с изображениями.
     * @param input входящее изображение
     * @return обработанное изображение
     */
    fun make(input: Bitmap): Bitmap {
        image = input.copy(input.config, true)
        matrix = Array(image.height) { DoubleArray(image.width) }
        render()
        return image
    }

    /**
     * Функция обработки изображения.
     */
    private fun render() {
        var average = 0.0

        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val pixel = image[i, j]
                matrix[j][i] = Color.red(pixel) * 0.299 + Color.green(pixel) * 0.587 + Color.blue(pixel) * 0.114
                average += matrix[j][i]
            }
        }

        average /= image.width * image.height

        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val pixel = image[i, j]
                val red = Color.red(pixel).toDouble() * ((average - matrix[j][i]) / average)
                val green = Color.green(pixel).toDouble() * ((average - matrix[j][i]) / average)
                val blue = Color.blue(pixel).toDouble() * ((average - matrix[j][i]) / average)
                val resultColor = Color.argb(255, red.toInt(), green.toInt(), blue.toInt())

                image[i, j] = resultColor
            }
        }
    }
}