package com.vladiknt.piceditnt

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

/**
 * Класс Activity с информацией о пользователе.
 * @autor Владислав Васильев
 * @version 1.0
 */
class MainActivity : AppCompatActivity() {
    /**
     * Функция, вызываемая при создании Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
}