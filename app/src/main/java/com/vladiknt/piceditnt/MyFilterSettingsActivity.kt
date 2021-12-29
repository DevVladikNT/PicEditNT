package com.vladiknt.piceditnt

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

class MyFilterSettingsActivity : AppCompatActivity() {
    lateinit var processing: Deferred<Unit>
    var active = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_filter_settings)

        findViewById<SeekBar>(R.id.RL).let {
            it.progress = MyFilterSettings.kRedLow
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.GL).let {
            it.progress = MyFilterSettings.kGreenLow
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.BL).let {
            it.progress = MyFilterSettings.kBlueLow
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.RM).let {
            it.progress = MyFilterSettings.kRedMiddle
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.GM).let {
            it.progress = MyFilterSettings.kGreenMiddle
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.BM).let {
            it.progress = MyFilterSettings.kBlueMiddle
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.RH).let {
            it.progress = MyFilterSettings.kRedHigh
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.GH).let {
            it.progress = MyFilterSettings.kGreenHigh
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        findViewById<SeekBar>(R.id.BH).let {
            it.progress = MyFilterSettings.kBlueHigh
            it.setOnSeekBarChangeListener(seekBarChangeListener)
        }
        CoroutineScope(Dispatchers.Default).async {
            // todo
            MyFilterSettings.loadInfo(File(Environment.getDataDirectory(), MyFilterSettings.file))
            findViewById<ImageView>(R.id.settingsImageSrc).setImageBitmap(
                MyFilter.make(BitmapFactory.decodeResource(resources, R.drawable.original))
            )
        }
    }

    private val seekBarChangeListener: OnSeekBarChangeListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {}
        override fun onStartTrackingTouch(seekBar: SeekBar) {}
        override fun onStopTrackingTouch(seekBar: SeekBar) {
            when (seekBar.id) {
                R.id.RL -> MyFilterSettings.kRedLow = seekBar.progress
                R.id.GL -> MyFilterSettings.kGreenLow = seekBar.progress
                R.id.BL -> MyFilterSettings.kBlueLow = seekBar.progress
                R.id.RM -> MyFilterSettings.kRedMiddle = seekBar.progress
                R.id.GM -> MyFilterSettings.kGreenMiddle = seekBar.progress
                R.id.BM -> MyFilterSettings.kBlueMiddle = seekBar.progress
                R.id.RH -> MyFilterSettings.kRedHigh = seekBar.progress
                R.id.GH -> MyFilterSettings.kGreenHigh = seekBar.progress
                R.id.BH -> MyFilterSettings.kBlueHigh = seekBar.progress
            }
            // todo if processing then cancel
            if (active) processing.cancel()
            active = true
            processing = CoroutineScope(Dispatchers.Default).async {
                findViewById<ImageView>(R.id.settingsImageSrc).setImageBitmap(
                    MyFilter.make(BitmapFactory.decodeResource(resources, R.drawable.original))
                )
                active = false
            }
        }
    }

    fun saveInfo(view: View?) {
        // todo
        MyFilterSettings.saveInfo(File(Environment.getDataDirectory(), MyFilterSettings.file))
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
    }
}