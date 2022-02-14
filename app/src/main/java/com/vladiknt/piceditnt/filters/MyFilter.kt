package com.vladiknt.piceditnt.filters

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.set
import com.vladiknt.piceditnt.MyFilterSettingsActivity

object MyFilter {
    private lateinit var image: Bitmap

    fun make(input: Bitmap): Bitmap {
        image = input.copy(input.config, true)
        render()
        return image
    }

    private fun render() {
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val pixel = image[i, j]
                val color: Int =  when (val value = (Color.red(pixel) * 0.299 + Color.green(pixel) * 0.587 + Color.blue(pixel) * 0.114).toInt()) {
                    in 0..85 -> Color.argb(
                            Color.alpha(pixel),
                            (0.7*Color.red(pixel) + 0.3*MyFilterSettingsActivity.kRedLow).toInt(),
                            (0.7*Color.green(pixel) + 0.3*MyFilterSettingsActivity.kGreenLow).toInt(),
                            (0.7*Color.blue(pixel) + 0.3*MyFilterSettingsActivity.kBlueLow).toInt()
                    )
                    in 85..170 -> Color.argb(
                            Color.alpha(pixel),
                            (0.7*Color.red(pixel) + 0.3*MyFilterSettingsActivity.kRedMiddle).toInt(),
                            (0.7*Color.green(pixel) + 0.3*MyFilterSettingsActivity.kGreenMiddle).toInt(),
                            (0.7*Color.blue(pixel) + 0.3*MyFilterSettingsActivity.kBlueMiddle).toInt()
                    )
                    in 170..255 -> Color.argb(
                            Color.alpha(pixel),
                            (0.7*Color.red(pixel) + 0.3*MyFilterSettingsActivity.kRedHigh).toInt(),
                            (0.7*Color.green(pixel) + 0.3*MyFilterSettingsActivity.kGreenHigh).toInt(),
                            (0.7*Color.blue(pixel) + 0.3*MyFilterSettingsActivity.kBlueHigh).toInt()
                    )
                    else -> Color.argb(Color.alpha(pixel), value, value, value)
                }
                image[i, j] = color
            }
        }
    }
}