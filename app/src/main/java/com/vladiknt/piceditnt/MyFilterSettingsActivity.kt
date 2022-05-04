package com.vladiknt.piceditnt

import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vladiknt.piceditnt.filters.MyFilter
import kotlinx.coroutines.*
import java.io.File

/**
 * Класс Activity, отвечающей за настройку параметров алгоритма.
 * @autor Владислав Васильев
 * @version 1.0
 */
class MyFilterSettingsActivity : AppCompatActivity() {
    /** Поле static поля */
    companion object {
        var kRedLow = 64
        var kGreenLow = 64
        var kBlueLow = 64
        var kRedMiddle = 96
        var kGreenMiddle = 96
        var kBlueMiddle = 96
        var kRedHigh = 128
        var kGreenHigh = 128
        var kBlueHigh = 128

        lateinit var pref: SharedPreferences
        val APP_PREFERENCES = "myfiltersettings"

        fun loadInfo() {
            kRedLow = pref.getInt("RL", 64)
            kGreenLow = pref.getInt("GL", 64)
            kBlueLow = pref.getInt("BL", 64)
            kRedMiddle = pref.getInt("RM", 96)
            kGreenMiddle = pref.getInt("GM", 96)
            kBlueMiddle = pref.getInt("BM", 96)
            kRedHigh = pref.getInt("RH", 128)
            kGreenHigh = pref.getInt("GH", 128)
            kBlueHigh = pref.getInt("BH", 128)
        }
    }

    /** Поле текущая задача */
    lateinit var processing: Deferred<Unit>
    /** Поле активна ли задача */
    var active = false

    /**
     * Функция, вызываемая при создании Activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_filter_settings)

        findViewById<SeekBar>(R.id.RL).let {
            it.progress = kRedLow
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.GL).let {
            it.progress = kGreenLow
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.BL).let {
            it.progress = kBlueLow
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.RM).let {
            it.progress = kRedMiddle
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.GM).let {
            it.progress = kGreenMiddle
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.BM).let {
            it.progress = kBlueMiddle
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.RH).let {
            it.progress = kRedHigh
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.GH).let {
            it.progress = kGreenHigh
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.BH).let {
            it.progress = kBlueHigh
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        CoroutineScope(Dispatchers.Default).async {
            findViewById<ImageView>(R.id.settingsImageSrc).setImageBitmap(
                MyFilter.make(BitmapFactory.decodeResource(resources, R.drawable.original_scaled))
            )
        }
    }

    /**
     * Функция изменения значений ползунков и перерисовки preview.
     */
    private val seekBarChangeListener: OnSeekBarChangeListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {
            when (seekBar.id) {
                R.id.RL -> kRedLow = seekBar.progress
                R.id.GL -> kGreenLow = seekBar.progress
                R.id.BL -> kBlueLow = seekBar.progress
                R.id.RM -> kRedMiddle = seekBar.progress
                R.id.GM -> kGreenMiddle = seekBar.progress
                R.id.BM -> kBlueMiddle = seekBar.progress
                R.id.RH -> kRedHigh = seekBar.progress
                R.id.GH -> kGreenHigh = seekBar.progress
                R.id.BH -> kBlueHigh = seekBar.progress
            }

            // todo if processing then cancel
            if (active) processing.cancel()
            active = true
            processing = CoroutineScope(Dispatchers.Default).async {
                findViewById<ImageView>(R.id.settingsImageSrc).setImageBitmap(
                    MyFilter.make(BitmapFactory.decodeResource(resources, R.drawable.original_scaled))
                )
                active = false
            }
        }
    }

    /**
     * Функция сохранения параметров алгоритма.
     */
    fun saveInfo(view: View?) {
        val saver = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE).edit()
        saver.putInt("RL", kRedLow)
        saver.putInt("GL", kGreenLow)
        saver.putInt("BL", kBlueLow)
        saver.putInt("RM", kRedMiddle)
        saver.putInt("GM", kGreenMiddle)
        saver.putInt("BM", kBlueMiddle)
        saver.putInt("RH", kRedHigh)
        saver.putInt("GH", kGreenHigh)
        saver.putInt("BH", kBlueHigh)
        saver.apply()
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
    }

    /**
     * Функция сброса параметров алгоритма.
     */
    fun resetInfo(view: View?) {
        kRedLow = 64
        kGreenLow = 64
        kBlueLow = 64
        kRedMiddle = 96
        kGreenMiddle = 96
        kBlueMiddle = 96
        kRedHigh = 128
        kGreenHigh = 128
        kBlueHigh = 128
        saveInfo(null)
        findViewById<SeekBar>(R.id.RL).setProgress(64, true)
        findViewById<SeekBar>(R.id.GL).setProgress(64, true)
        findViewById<SeekBar>(R.id.BL).setProgress(64, true)
        findViewById<SeekBar>(R.id.RM).setProgress(96, true)
        findViewById<SeekBar>(R.id.GM).setProgress(96, true)
        findViewById<SeekBar>(R.id.BM).setProgress(96, true)
        findViewById<SeekBar>(R.id.RH).setProgress(128, true)
        findViewById<SeekBar>(R.id.GH).setProgress(128, true)
        findViewById<SeekBar>(R.id.BH).setProgress(128, true)
        Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show()
    }
}