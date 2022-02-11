package com.vladiknt.piceditnt

import android.util.Log
import java.io.*

object MyFilterSettings {
    var kRedLow = 64
    var kGreenLow = 64
    var kBlueLow = 64
    var kRedMiddle = 96
    var kGreenMiddle = 96
    var kBlueMiddle = 96
    var kRedHigh = 128
    var kGreenHigh = 128
    var kBlueHigh = 128
    var file = "myFilterData.txt"
    var hasFile = false

    fun loadInfo(file: File) {
        if (file.exists()) {
            try {
                val br = BufferedReader(FileReader(file))
                var buff = ""
                var i = 0
                while (true) {
                    buff = br.readLine()
                    if (buff == "%") {
                        hasFile = true
                        break
                    }
                    when (i) {
                        0 -> kRedLow = buff.toInt()
                        1 -> kGreenLow = buff.toInt()
                        2 -> kBlueLow = buff.toInt()
                        3 -> kRedMiddle = buff.toInt()
                        4 -> kGreenMiddle = buff.toInt()
                        5 -> kBlueMiddle = buff.toInt()
                        6 -> kRedHigh = buff.toInt()
                        7 -> kGreenHigh = buff.toInt()
                        8 -> kBlueHigh = buff.toInt()
                    }
                    i++
                }
                br.close()
                Log.d("files", "loaded")
            } catch (e: Exception) {
                Log.d("files", "didn`t load")
                saveInfo(file)
            }
        }
    }

    fun saveInfo(file: File) {
        try {
            val bw = BufferedWriter(FileWriter(file))
            bw.write(kRedLow)
            bw.newLine()
            bw.append(kGreenLow.toString())
            bw.newLine()
            bw.append(kBlueLow.toString())
            bw.newLine()
            bw.append(kRedMiddle.toString())
            bw.newLine()
            bw.append(kGreenMiddle.toString())
            bw.newLine()
            bw.append(kBlueMiddle.toString())
            bw.newLine()
            bw.append(kRedHigh.toString())
            bw.newLine()
            bw.append(kGreenHigh.toString())
            bw.newLine()
            bw.append(kBlueHigh.toString())
            bw.newLine()
            bw.append("%")
            bw.flush()
            bw.close()
            Log.d("files", "saved")
        } catch (e: Exception) {
            Log.d("files", "didn`t save")
        }
    }
}