package com.vladiknt.piceditnt

import android.app.ActivityOptions
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.vladiknt.piceditnt.filters.*
import kotlinx.coroutines.*

/**
 * Класс Activity с информацией о пользователе.
 * @autor Владислав Васильев
 * @version 1.0
 */
class MainActivity : AppCompatActivity() {
    private var db: FirebaseFirestore? = null
    private var user: FirebaseUser? = null
    private var uid = ""

    /**
     * Функция, вызываемая при создании Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(/*context=*/this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            SafetyNetAppCheckProviderFactory.getInstance()
        )

        db = FirebaseFirestore.getInstance()
        user = FirebaseAuth.getInstance().currentUser

        if (intent.getStringExtra("id") != null) {
            uid = intent.getStringExtra("id")!!
            findViewById<LinearLayout>(R.id.findUserButton).visibility = View.GONE
            findViewById<LinearLayout>(R.id.makePhotoButton).visibility = View.GONE
        } else
            uid = user!!.uid

        // Получаем информацию о пользователе
        val userNickname = findViewById<TextView>(R.id.userNickname)
        val userId = findViewById<TextView>(R.id.userId)
        db!!.collection("users").document(uid).get()
            .addOnCompleteListener { task: Task<DocumentSnapshot?> ->
                if (task.isSuccessful) {
                    val getDoc = task.result
                    val nick = getDoc!!["nickname"].toString()
                    val avatarPath = getDoc["avatar"].toString()
                    val imgPath1 = getDoc["img1"].toString()
                    val imgPath2 = getDoc["img2"].toString()
                    val imgPath3 = getDoc["img3"].toString()
                    val imgPath4 = getDoc["img4"].toString()
                    val imgPath5 = getDoc["img5"].toString()

                    userNickname.clearComposingText()
                    userNickname.text = nick
                    userId.clearComposingText()
                    userId.text = "id: $uid"

                    // Загрузка изображений в фоне
                    val THREE_MEGABYTES = (3 * 1024 * 1024).toLong()
                    FirebaseStorage.getInstance().reference.child(avatarPath).getBytes(THREE_MEGABYTES)
                        .addOnSuccessListener { bytesPrm: ByteArray ->
                            val bitmap = BitmapFactory.decodeByteArray(bytesPrm, 0, bytesPrm.size)
                            findViewById<ImageView>(R.id.userAvatar).setImageBitmap(bitmap)
                        }
                    FirebaseStorage.getInstance().reference.child(imgPath1).getBytes(THREE_MEGABYTES)
                        .addOnSuccessListener { bytesPrm: ByteArray ->
                            val bitmap = BitmapFactory.decodeByteArray(bytesPrm, 0, bytesPrm.size)
                            findViewById<ImageView>(R.id.userPhoto1).setImageBitmap(bitmap)
                        }
                    FirebaseStorage.getInstance().reference.child(imgPath2).getBytes(THREE_MEGABYTES)
                        .addOnSuccessListener { bytesPrm: ByteArray ->
                            val bitmap = BitmapFactory.decodeByteArray(bytesPrm, 0, bytesPrm.size)
                            findViewById<ImageView>(R.id.userPhoto2).setImageBitmap(bitmap)
                        }
                    FirebaseStorage.getInstance().reference.child(imgPath3).getBytes(THREE_MEGABYTES)
                        .addOnSuccessListener { bytesPrm: ByteArray ->
                            val bitmap = BitmapFactory.decodeByteArray(bytesPrm, 0, bytesPrm.size)
                            findViewById<ImageView>(R.id.userPhoto3).setImageBitmap(bitmap)
                        }
                    FirebaseStorage.getInstance().reference.child(imgPath4).getBytes(THREE_MEGABYTES)
                        .addOnSuccessListener { bytesPrm: ByteArray ->
                            val bitmap = BitmapFactory.decodeByteArray(bytesPrm, 0, bytesPrm.size)
                            findViewById<ImageView>(R.id.userPhoto4).setImageBitmap(bitmap)
                        }
                    FirebaseStorage.getInstance().reference.child(imgPath5).getBytes(THREE_MEGABYTES)
                        .addOnSuccessListener { bytesPrm: ByteArray ->
                            val bitmap = BitmapFactory.decodeByteArray(bytesPrm, 0, bytesPrm.size)
                            findViewById<ImageView>(R.id.userPhoto5).setImageBitmap(bitmap)
                        }
                }
            }
    }

    /**
     * Функция, вызываемая по нажатию кнопки "Make photo".
     * Переводит пользователя в Activity для выбора фильтра.
     * @see FiltersActivity
     */
    fun chooseFilter(view: View?) {
        val act = Intent(this, FiltersActivity::class.java)
        startActivity(act, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    /**
     * Функция, копирующая id пользователя по нажатию на TextView.
     */
    fun copyId(view: View?) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("", (view as TextView).text.split(" ")[1])
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "User`s ID was copied", Toast.LENGTH_SHORT).show()
    }

    /**
     * Функция, которая ищет пользователя по id.
     */
    fun findUser(view: View?) {
        val id = findViewById<EditText>(R.id.userIdField).text.toString()
        val act = Intent(this, MainActivity::class.java)
        act.putExtra("id", id)
        startActivity(act, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}