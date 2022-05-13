package com.vladiknt.piceditnt

import android.app.ActivityOptions
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Класс Activity с информацией о пользователе.
 * @autor Владислав Васильев
 * @version 1.0
 */
class MainActivity : AppCompatActivity() {
    var db: FirebaseFirestore? = null
    var user: FirebaseUser? = null

    /**
     * Функция, вызываемая при создании Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()
        user = FirebaseAuth.getInstance().currentUser

        // Устанавливаем никнейм
        val userNickname = findViewById<TextView>(R.id.userNickname)
        val userId = findViewById<TextView>(R.id.userId)
        db!!.collection("users").document(user!!.uid).get()
            .addOnCompleteListener { task: Task<DocumentSnapshot?> ->
                if (task.isSuccessful) {
                    val getDoc = task.result
                    val nick = getDoc!!["nickname"].toString()
                    userNickname.clearComposingText()
                    userNickname.text = nick
                    userId.clearComposingText()
                    userId.text = "id: ${user!!.uid}"
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
}