import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.get
import androidx.core.graphics.set

object BlackWhiteFilter {
    private lateinit var image: Bitmap
    private lateinit var result: Bitmap

    fun make(input: Bitmap): Bitmap {
        image = input.copy(input.config, true)
        result = input.copy(input.config, true)
        input.recycle()
        render()
        image.recycle()
        return result
    }

    private fun render() {
        for (i in 0 until image.width) {
            for (j in 0 until image.height) {
                val pixel = image[i, j]
                val value = (Color.red(pixel) * 0.299 + Color.green(pixel) * 0.587 + Color.blue(pixel) * 0.114).toInt()
                val color = Color.argb(Color.alpha(pixel), value, value, value)
                result[i, j] = color
            }
        }
    }
}