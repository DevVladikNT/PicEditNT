package com.vladiknt.piceditnt

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class RegisterActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        findViewById<EditText>(R.id.regPass1).addTextChangedListener {
            if (it.toString().length < 8) findViewById<TextView>(R.id.regPass1Check).background = resources.getColor(R.color.incorrect).toDrawable()
            else {
                if (it.toString().toLowerCase() == it.toString() || it.toString().toUpperCase() == it.toString())
                    findViewById<TextView>(R.id.regPass1Check).background = resources.getColor(R.color.incorrect).toDrawable()
                else findViewById<TextView>(R.id.regPass1Check).background = resources.getColor(R.color.correct).toDrawable()
            }
        }

        findViewById<EditText>(R.id.regPass2).addTextChangedListener {
            if (it.toString() != findViewById<EditText>(R.id.regPass1).text.toString()) findViewById<TextView>(R.id.regPass2Check).background = resources.getColor(R.color.incorrect).toDrawable()
            else findViewById<TextView>(R.id.regPass2Check).background = resources.getColor(R.color.correct).toDrawable()
        }
    }

//    fun goToLicense(view: View?) {
//        val lic = Intent(this, LicenseActivity::class.java)
//        startActivity(lic)
//    }

    fun registerUser(view: View?) {
        var et = findViewById<EditText>(R.id.regMail)
        val email = et.text.toString()
        et = findViewById(R.id.regPass1)
        val pass1 = et.text.toString()
        et = findViewById(R.id.regPass2)
        val pass2 = et.text.toString()
        et = findViewById(R.id.regNickname)
        val nickname = et.text.toString()

        // Проверка пароля
        // TODO доделать
        var correctPassword = false
        et = findViewById(R.id.regPass1)
        if (et.toString().length >= 8 && et.toString().toLowerCase() != et.toString() && et.toString().toUpperCase() != et.toString())
            correctPassword = true
        if(!correctPassword) {
            Toast.makeText(this, "Weak password", Toast.LENGTH_SHORT).show()
            return
        }

        // Подготовка файлов пользователя для добавления в БД
        val userInfo: MutableMap<String, Any> = HashMap()
        userInfo["nickname"] = nickname
        userInfo["img1"] = ""
        userInfo["img2"] = ""
        userInfo["img3"] = ""
        userInfo["img4"] = ""
        userInfo["img5"] = ""
        // Регистрация
        if (pass1 == pass2) {
            mAuth!!.createUserWithEmailAndPassword(email, pass1)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        db!!.collection("users").document(mAuth!!.currentUser!!.uid).set(userInfo)
                            .addOnCompleteListener { task1: Task<Void?> ->
                                if (task1.isSuccessful) {
                                    mAuth!!.currentUser!!.sendEmailVerification()
                                    Toast.makeText(this, "Please, activate your profile in letter", Toast.LENGTH_SHORT).show()
                                    finish()
                                } else Toast.makeText(this, "Error while adding user info", Toast.LENGTH_SHORT).show()
                            }
                    } else Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
        } else {
            et = findViewById(R.id.regPass1)
            et.clearComposingText()
            et = findViewById(R.id.regPass2)
            et.clearComposingText()
            Toast.makeText(this, "Enter your passwords again", Toast.LENGTH_SHORT).show()
        }
    }
}