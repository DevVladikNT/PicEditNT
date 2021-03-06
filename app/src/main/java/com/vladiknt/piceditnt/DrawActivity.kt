package com.vladiknt.piceditnt

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Images
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.scale
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.vladiknt.piceditnt.filters.*
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException
import java.io.InputStream

/**
 * Класс Activity, отвечающей за обработку изображений.
 * @autor Владислав Васильев
 * @version 1.0
 */
class DrawActivity : AppCompatActivity() {
    /** Поле масштабирование */
    private var scale = 1
    /** Поле изображение */
    private var selectedImage = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)

    private var mAuth: FirebaseAuth? = null
    private var mUser: FirebaseUser? = null

    /**
     * Функция, вызываемая при создании Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draw)
        if (intent.extras!!["filterName"] == "My")
            findViewById<TextView>(R.id.MyFilterEdit).visibility = View.VISIBLE
        MyFilterSettingsActivity.loadInfo()

        mAuth = FirebaseAuth.getInstance()
        mUser = mAuth!!.currentUser
    }

    /**
     * Функция, вызываемая по нажатию кнопки настройки фильтра.
     * Создает новую Activity для настройки параметров фильтра.
     * @see MyFilterSettingsActivity
     */
    fun myFilterSettingsButton(view: View?) {
        val intent = Intent(this, MyFilterSettingsActivity::class.java)
        startActivity(intent)
    }

    /**
     * Функция выбора изображения из памяти устройства.
     */
    fun pickImage(view: View?) {
        val getPic = Intent(Intent.ACTION_PICK)
        getPic.type = "image/*"
        startActivityForResult(getPic, 1)
    }

    /**
     * Функция обработки изображения выбранным фильтром.
     * @see BlackWhiteFilter
     * @see CircuitFilter
     * @see ColorShiftsFilter
     * @see CyberpunkFilter
     * @see DarkFilter
     * @see DarkYellowFilter
     * @see DefocusingFilter
     * @see LiningFilter
     * @see MyFilter
     * @see NeonFilter
     * @see PetrolFilter
     * @see PixelFilter
     * @see ScanDocFilter
     */
    fun processImage(view: View?) {
        try {
            val imageView = findViewById<ImageView>(R.id.imageSrc)
            val scaledSelectedImage = selectedImage.scale(selectedImage.width/scale, selectedImage.height/scale)
            Toast.makeText(this, "Wait please", Toast.LENGTH_SHORT).show()
            // Обрабатываем изображение
            val time = System.currentTimeMillis()
            // Вызываем корутину в потоке UI
            val job = CoroutineScope(Dispatchers.Main).async {
                // Вычисления запускаем в фоновом потоке
                withContext(Dispatchers.Default) {
                    selectedImage = when (intent.extras!!["filterName"]) {
                        "BlackWhite" -> BlackWhiteFilter.make(scaledSelectedImage)
                        "Circuit" -> CircuitFilter.make(scaledSelectedImage)
                        "ColorShifts" -> ColorShiftsFilter.make(scaledSelectedImage)
                        "Contrast" -> ContrastFilter.make(scaledSelectedImage)
                        "Cyberpunk" -> CyberpunkFilter.make(scaledSelectedImage)
                        "Dark" -> DarkFilter.make(scaledSelectedImage)
                        "DarkYellow" -> DarkYellowFilter.make(scaledSelectedImage)
                        "Defocusing" -> DefocusingFilter.make(scaledSelectedImage)
                        "Lining" -> LiningFilter.make(scaledSelectedImage)
                        "My" -> MyFilter.make(scaledSelectedImage)
                        "Neon" -> NeonFilter.make(scaledSelectedImage)
                        "Petrol" -> PetrolFilter.make(scaledSelectedImage)
                        "Pixel" -> PixelFilter.make(scaledSelectedImage)
                        "ScanDoc" -> ScanDocFilter.make(scaledSelectedImage)
                        else -> selectedImage
                    }
                }
                // Возвращаемся в UI чтобы обновить картинку
                // TODO add logo
                imageView.setImageBitmap(selectedImage)
            }
            job.invokeOnCompletion {
                Log.d("Time", "${(System.currentTimeMillis() - time) / 1000.0}")
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to process image", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Функция сохранения изображения в память устройства.
     */
    fun saveImage(view: View?) {
        if (selectedImage.height == 1) {
            Toast.makeText(this, "Pick image firstly", Toast.LENGTH_SHORT).show()
            return
        }
        if (mUser != null) {
            val act = Intent(this, SaveActivity::class.java)
            val stream = ByteArrayOutputStream()
            selectedImage.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            act.putExtra("image", stream.toByteArray())
            startActivity(act)
        } else {
            Images.Media.insertImage(
                contentResolver,
                selectedImage,
                "${System.currentTimeMillis()}",
                "image generated by PicEditNT"
            )
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Функция, вызываемая после возвращения в данную Activity.
     * @see DrawActivity.pickImage
     */
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
            }
        }
        imageView.setImageBitmap(selectedImage)
    }
    
}