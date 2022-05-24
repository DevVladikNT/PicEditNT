package com.vladiknt.piceditnt.filters

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.set
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

/**
 * Фильтр для повышения контрастности изображения.
 * @autor Владислав Васильев
 * @version 1.0
 */
object ContrastFilter {
    /** Поле изображение */
    private lateinit var image: Bitmap

    /**
     * Функция, работающая с изображениями.
     * @param input входящее изображение
     * @return обработанное изображение
     */
    fun make(input: Bitmap): Bitmap {
        image = input.copy(input.config, true)
        render()
        return image
    }

    /**
     * Функция обработки изображения.
     */
    private fun render() {
        val k = 0.8
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val pixel = image[i, j]
                val red = if (Color.red(pixel) > 128)
                    256 - (256 - Color.red(pixel)) * k
                else
                    Color.red(pixel) * k
                val green = if (Color.green(pixel) > 128)
                    256 - (256 - Color.green(pixel)) * k
                else
                    Color.green(pixel) * k
                val blue = if (Color.blue(pixel) > 128)
                    256 - (256 - Color.blue(pixel)) * k
                else
                    Color.green(pixel) * k
                image[i, j] = Color.argb(255, red.toInt(), green.toInt(), blue.toInt())
            }
        }
    }
}