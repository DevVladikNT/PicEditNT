package com.vladiknt.piceditnt.filters

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.set
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

/**
 * Черно-белый фильтр.
 * @autor Владислав Васильев
 * @version 1.0
 */
object BlackWhiteFilter {
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
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val pixel = image[i, j]
                val value = (Color.red(pixel) * 0.299 + Color.green(pixel) * 0.587 + Color.blue(pixel) * 0.114).toInt()
                val color = Color.argb(Color.alpha(pixel), value, value, value)
                image[i, j] = color
            }
        }
    }
}