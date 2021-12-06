package com.vladiknt.piceditnt

import ColorShiftsFilter
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.getExtras
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.core.graphics.toColor
import java.io.FileNotFoundException
import java.io.InputStream


class DrawActivity : AppCompatActivity() {
    var scale = 4
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
        selectedImage = when (intent.extras!!["filterName"]) {
            "BlackWhite" -> BlackWhiteFilter.make(scaledSelectedImage)
            "ColorShifts" -> ColorShiftsFilter.make(scaledSelectedImage)
            //"Cyberpunk" ->
            //"Defocusing" ->
            //"Rainbow" ->
            else -> selectedImage
        }
        // TODO add logo
        imageView.setImageBitmap(selectedImage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView = findViewById<ImageView>(R.id.imageSrc)
        if (requestCode == 1) {
            try {
                //Получаем URI изображения, преобразуем его в Bitmap
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