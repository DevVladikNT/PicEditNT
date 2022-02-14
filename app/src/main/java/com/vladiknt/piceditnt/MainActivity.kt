package com.vladiknt.piceditnt

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vladiknt.piceditnt.MyFilterSettingsActivity.Companion.APP_PREFERENCES


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyFilterSettingsActivity.pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun setFilter(view: View?) {
        val act = Intent(this, DrawActivity::class.java)
        val filterName = when (view!!.id) {
            R.id.BlackWhite -> "BlackWhite"
            R.id.ColorShifts -> "ColorShifts"
            R.id.Cyberpunk -> "Cyberpunk"
            R.id.Defocusing -> "Defocusing"
            R.id.Circuit -> "Circuit"
            R.id.MyFilter -> "My"
            else -> ""
        }
        act.putExtra("filterName", filterName)
        startActivity(act, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }
}