package com.vladiknt.piceditnt

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.TextureView
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.vladiknt.piceditnt.MyFilterSettingsActivity.Companion.APP_PREFERENCES

/**
 * Класс Activity для выбора фильтров.
 * @autor Владислав Васильев
 * @version 1.0
 */
class FiltersActivity : AppCompatActivity() {
    /**
     * Функция, вызываемая при создании Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filters)
    }

    /**
     * Функция, вызываемая по нажатию кнопки при выборе фильтра.
     * Алгоритм определяет какой фильтр был выбран и передает эту
     * информацию, создавая новую Activity.
     * @see DrawActivity
     */
    fun setFilter(view: View?) {
        val act = Intent(this, DrawActivity::class.java)
        val filterName = when (view!!.id) {
            R.id.BlackWhite -> "BlackWhite"
            R.id.Circuit -> "Circuit"
            R.id.ColorShifts -> "ColorShifts"
            R.id.Contrast -> "Contrast"
            R.id.Cyberpunk -> "Cyberpunk"
            R.id.Dark -> "Dark"
            R.id.DarkYellow -> "DarkYellow"
            R.id.Defocusing -> "Defocusing"
            R.id.Lining -> "Lining"
            R.id.MyFilter -> "My"
            R.id.Neon -> "Neon"
            R.id.Petrol -> "Petrol"
            R.id.Pixel -> "Pixel"
            R.id.ScanDoc -> "ScanDoc"
            else -> ""
        }
        act.putExtra("filterName", filterName)
        startActivity(act, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

    /**
     * Функция, раскрывающая остальную часть фильтров.
     */
    fun showOtherFilters(view: View?) {
        findViewById<TextView>(R.id.showButton).visibility = View.GONE
        findViewById<LinearLayout>(R.id.otherFilters).visibility = View.VISIBLE
    }
}