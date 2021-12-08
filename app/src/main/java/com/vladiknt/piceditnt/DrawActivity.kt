package com.vladiknt.piceditnt

import com.vladiknt.piceditnt.filters.ColorShiftsFilter
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vladiknt.piceditnt.filters.BlackWhiteFilter
import com.vladiknt.piceditnt.filters.CyberpunkFilter
import kotlinx.coroutines.*
import java.io.FileNotFoundException
import java.io.InputStream


class DrawActivity : AppCompatActivity() {
    var scale = 4
    private val scope = CoroutineScope(Dispatchers.Default)
    private var selectedImage = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)
    }

    fun pickImage(view: View?) {
        val getPic = Intent(Intent.ACTION_PICK)
        getPic.type = "image/*"
        startActivityForResult(getPic, 1)
    }

    fun processImage(view: View?) {
        if (selectedImage.height == 1) {
            Toast.makeText(this, "Pick image firstly", Toast.LENGTH_SHORT).show()
            return
        }
        val imageView = findViewById<ImageView>(R.id.imageSrc)
        val scaledSelectedImage = Bitmap.createScaledBitmap(selectedImage, selectedImage.width / scale, selectedImage.height / scale, false)
        Toast.makeText(this, "Wait please.", Toast.LENGTH_SHORT).show()
        // Обрабатываем изображение
        val time = System.currentTimeMillis()
        val job = scope.async {
            selectedImage = when (intent.extras!!["filterName"]) {
                "BlackWhite" -> BlackWhiteFilter.make(scaledSelectedImage)
                "ColorShifts" -> ColorShiftsFilter.make(scaledSelectedImage)
                "Cyberpunk" -> CyberpunkFilter.make(scaledSelectedImage)
                //"Defocusing" ->
                //"Rainbow" ->
                else -> selectedImage
            }
            // TODO add logo
            imageView.setImageBitmap(selectedImage)
        }
        job.invokeOnCompletion {
            Log.d("Time", "${(System.currentTimeMillis() - time)/1000.0}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView = findViewById<ImageView>(R.id.imageSrc)
        if (requestCode == 1) {
            try {
                // Получаем URI изображения, преобразуем его в Bitmap
                if (data != null) {
                    val imageUri: Uri? = data.data
                    val imageStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
                    selectedImage = BitmapFactory.decodeStream(imageStream)
                    imageView.setImageBitmap(selectedImage)
                }
            } catch (e: FileNotFoundException) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
        imageView.setImageBitmap(selectedImage)
    }
    
}