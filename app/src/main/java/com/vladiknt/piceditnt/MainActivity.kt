package com.vladiknt.piceditnt

import android.app.ActivityOptions
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
import androidx.core.graphics.get
import androidx.core.graphics.set
import androidx.core.graphics.toColor
import java.io.FileNotFoundException
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setFilter(view: View?) {
        val act = Intent(this, DrawActivity::class.java)
        val filterName = when (view!!.id) {
            R.id.BlackWhite -> "BlackWhite"
            R.id.CircleGeneration -> "CircleGeneration"
            R.id.ColorShifts -> "ColorShifts"
            R.id.Cyberpunk -> "Cyberpunk"
            R.id.Defocusing -> "Defocusing"
            R.id.Rainbow -> "Rainbow"
            else -> ""
        }
        act.putExtra("filterName", filterName)
        startActivity(act, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

}