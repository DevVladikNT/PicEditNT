package com.vladiknt.piceditnt.filters

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.set

/**
 * Фильтр расфокусировки.
 * @autor Владислав Васильев
 * @version 1.0
 */
object DefocusingFilter {
    /** Поле изображение */
    private lateinit var image: Bitmap
    /** Поле промежуточное изображение */
    private lateinit var current: Bitmap

    /**
     * Функция, работающая с изображениями.
     * @param input входящее изображение
     * @return обработанное изображение
     */
    fun make(input: Bitmap): Bitmap {
        image = input.copy(input.config, true)
        smallBlur()
        render()
        return image
    }

    /**
     * Функция обработки изображения.
     */
    private fun render() {
        val shift = if (image.height < image.width) image.height / 120 else image.width / 120
        for (i in shift until image.width - shift) {
            for (j in shift until image.height - shift) {
                val pixelA = image[i - shift, j]
                val pixelB = image[i, j - shift]
                val pixelC = image[i + shift, j]
                val pixelD = image[i, j + shift]
                val color = Color.argb(
                    Color.alpha(pixelA),
                    (Color.red(pixelA) * 0.25 + Color.red(pixelB) * 0.25 + Color.red(pixelC) * 0.25 + Color.red(pixelD) * 0.25).toInt(),
                    (Color.green(pixelA) * 0.25 + Color.green(pixelB) * 0.25 + Color.green(pixelC) * 0.25 + Color.green(pixelD) * 0.25).toInt(),
                    (Color.blue(pixelA) * 0.25 + Color.blue(pixelB) * 0.25 + Color.blue(pixelC) * 0.25 + Color.blue(pixelD) * 0.25).toInt(),
                )
                current[i, j] = color
            }
        }
        image = current
    }

    /**
     * Функция слабого размытия изображения.
     */
    private fun smallBlur() {
        current = image
        for (i in 2 until image.width - 2) {
            for (j in 2 until image.height - 2) {
                val p1 = image[i - 1, j]
                val p2 = image[i, j - 1]
                val p3 = image[i + 1, j]
                val p4 = image[i, j + 1]
                val p5 = image[i + 1, j + 1]
                val p6 = image[i - 1, j + 1]
                val p7 = image[i + 1, j - 1]
                val p8 = image[i - 1, j - 1]
                val p9 = image[i, j + 2]
                val p10 = image[i + 2, j]
                val p11 = image[i, j - 2]
                val p12 = image[i - 2, j]
                val p = image[i, j]
                val color = Color.argb(
                    Color.alpha(p),
                    (Color.red(p1) + Color.red(p2) + Color.red(p3) + Color.red(p4) + Color.red(p5) + Color.red(p6) + Color.red(p7) + Color.red(p8) + Color.red(p9) + Color.red(p10) + Color.red(p11) + Color.red(p12)) / 12,
                    (Color.green(p1) + Color.green(p2) + Color.green(p3) + Color.green(p4) + Color.green(p5) + Color.green(p6) + Color.green(p7) + Color.green(p8) + Color.green(p9) + Color.green(p10) + Color.green(p11) + Color.green(p12)) / 12,
                    (Color.blue(p1) + Color.blue(p2) + Color.blue(p3) + Color.blue(p4) + Color.blue(p5) + Color.blue(p6) + Color.blue(p7) + Color.blue(p8) + Color.blue(p9) + Color.blue(p10) + Color.blue(p11) + Color.blue(p12)) / 12,
                )
                current[i, j] = color
            }
        }
        image = current
    }
}