package com.example.healthyandfoodclean

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import com.example.healthyandfoodclean.R.*
import kotlin.properties.Delegates

class TrirdActivity : AppCompatActivity() {
   private lateinit var btnButton: Button
    private lateinit var btime: TextView
    private lateinit var btntime: Button
    private var MTRunning: Boolean = false
    private var MTTimelefttimills by Delegates.notNull<Long>()
    private lateinit var coutDownTimer: CountDownTimer
    private lateinit var buttonvalue: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_bow)
        val intent = getIntent()
        buttonvalue = intent.getStringExtra("value") ?: ""
        val intValue = if (buttonvalue.isNullOrEmpty()) {
            0 // or any default value you want to use
        } else {
            Integer.valueOf(buttonvalue)
        }
        when (intValue) {
            1 -> setContentView(layout.activity_bow)
            2 -> setContentView(layout.activity_bridge)
            3 -> setContentView(layout.activity_chair)
            4 -> setContentView(layout.activity_child)
            5 -> setContentView(layout.activity_cobbler)
            6 -> setContentView(layout.activity_cow)
            7 -> setContentView(layout.activity_plankji)
            8 -> setContentView(layout.activity_mouseji)

        }
        btnButton = findViewById(id.starttime)
        btime = findViewById(id.time)
        btntime = findViewById(id.starttime)

        btntime.setOnClickListener {
            if (MTRunning) {
                stoptimer()
            } else {
                starttime()
            }
        }

    }

    private fun stoptimer() {
        coutDownTimer.cancel()
        MTRunning = false
        btntime.setText("START")
    }

    private fun starttime() {
        val value1 = btime.text.toString()
        val num2: String
        val num3: String
        if(value1.length >= 5){
            num2 = value1.substring(0, 2)
            num3 = value1.substring(3, 5)
        } else {
            num2 = "00"
            num3 = "00"
        }
        // rest of the code that uses num2 and num3
        val number = Integer.valueOf(num2) * 60 + Integer.valueOf(num3)
        MTTimelefttimills = number * 1000L

        coutDownTimer = object : CountDownTimer(MTTimelefttimills, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                MTTimelefttimills = millisUntilFinished
                updateTimer()
            }

            override fun onFinish() {
                val newvalue = Integer.valueOf(buttonvalue) + 1

                if (newvalue <= 7) {
                    val intent = Intent(this@TrirdActivity, TrirdActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.putExtra("value", newvalue)

                } else {
                    val newvalue = 1

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.putExtra("value", newvalue)

                }

            }
        }.start()

        btnButton.setText("pause")
        MTRunning = true
        coutDownTimer.start()
        MTRunning = true
        btntime.setText("STOP")
    }


    private fun updateTimer() {
        val minutes = MTTimelefttimills / 60000
        val second = (MTTimelefttimills % 60000)/1000

        var TimeLeftText = ""
        if (minutes < 10)
            TimeLeftText = "00:"
        TimeLeftText = TimeLeftText + minutes + ":"
                TimeLeftText = "00:"
                TimeLeftText +=second
                btime.setText(TimeLeftText)


    }
    public fun OnBackPressed() {
        super.onBackPressed()
    }
}