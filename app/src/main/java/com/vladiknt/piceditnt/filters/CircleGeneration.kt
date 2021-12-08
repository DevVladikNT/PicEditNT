package com.vladiknt.piceditnt.filters

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.set

object CircleGeneration {
    private const val k = 5
    private var image = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
    private var current = Bitmap.createBitmap(k * 100, k * 100, Bitmap.Config.ARGB_8888)

    fun make(): Bitmap {
        // Заполняем квадрат белым цветом
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                image[i, j] = Color.WHITE
            }
        }
        // Закрашиваем половину верхнего левого квадрата
        val black = Color.BLACK
        for (i in 0 until image.width / 2) {
            for (j in 0 until i) {
                if (Math.random() < Math.random()) {
                    image[i, j] = black
                }
            }
        }
        symmetry()
        upscale()
        blur()
        redraw()
        return image
    }

    private fun symmetry() {
        // Отображаем относительно главной диагонали, чтобы полностью закрыть первый квадрат
        for (i in 0 until image.width / 2)
            for (j in 0 until i)
                image[j, i] = image[i, j]
        // Отображаем рисунок симетрично относительно оси ОУ
        for (i in 0 until image.width / 2)
            for (j in 0 until image.height / 2)
                image[image.width - i - 1, j] = image[i, j]
        // Отображаем рисунок симетрично относительно оси ОХ
        for (i in 0 until image.width)
            for (j in 0 until image.height / 2)
                image[i, image.height - j - 1] = image[i, j]
    }

    private fun upscale() {
        // Увеличиваем рисунок с 100х100 до 1000х1000
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val color = image[i, j]
                for (i1 in i * k until (i + 1) * k)
                    for (j1 in j * k until (j + 1) * k)
                        current[i1, j1] = color
            }
        }
        image = current
    }

    private fun blur() {
        // Блюрим фотку
        // Блюрим фотку
        current = image
        repeat(5) {
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

    private fun redraw() {
        // Рисуем новую картинку по очертаниям
        for (i in 2 until image.width) {
            for (j in 2 until image.height) {
                var color = image[i, j]
                color =
                        if (Color.red(color) > 96) Color.WHITE
                        else Color.BLACK
                image[i, j] = color
            }
        }
    }
}