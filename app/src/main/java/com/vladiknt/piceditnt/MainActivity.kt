package com.vladiknt.piceditnt

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


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