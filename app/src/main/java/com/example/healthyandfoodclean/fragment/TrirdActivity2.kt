package com.example.healthyandfoodclean

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import com.example.healthyandfoodclean.R.*
import kotlin.properties.Delegates

class TrirdActivity2 : AppCompatActivity() {
    private lateinit var btnButton: Button
    private lateinit var btime: TextView
    private lateinit var btntime: Button
    private var MTRunning: Boolean = false
    private var MTTimelefttimills by Delegates.notNull<Long>()
    private lateinit var coutDownTimer: CountDownTimer
    private lateinit var buttonvalue: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bow)
        val intent = getIntent()
        //get gia tri
        buttonvalue = intent.getStringExtra("value") ?: ""
        val intValue = if (buttonvalue.isNullOrEmpty()) {
            0// hoặc bất kỳ giá trị mặc định nào bạn muốn sử dụng
        } else {
            Integer.valueOf(buttonvalue)
        }
        //kiem tra hoat dong thuc hien an xa
        when (intValue) {
            1 -> setContentView(R.layout.activity_bow2)
            2 -> setContentView(R.layout.activity_bridge2)
            3 -> setContentView(R.layout.activity_chair)
            4 -> setContentView(R.layout.activity_child)
            5 -> setContentView(R.layout.activity_cobbler)
            6 -> setContentView(R.layout.activity_cow)
            7 -> setContentView(R.layout.activity_plankji)
            8 -> setContentView(R.layout.activity_mouseji)

        }
       btnButton = findViewById(R.id.starttime)
        btime = findViewById(R.id.time)
        btntime = findViewById(R.id.starttime)
//thuc thi hoat dong thuc hien bai tap
        btntime.setOnClickListener {
            if (MTRunning) {
                stoptimer()
            } else {
                starttime()
            }
        }

    }
//dung dem
    private fun stoptimer() {
        coutDownTimer.cancel()
        MTRunning = false
        btntime.setText("START")
    }
//bat dau dem
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
    // phần còn lại của mã sử dụng num2 và num3
        val number = Integer.valueOf(num2) * 60 + Integer.valueOf(num3)
        MTTimelefttimills = number * 1000L

        coutDownTimer = object : CountDownTimer(MTTimelefttimills, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                MTTimelefttimills = millisUntilFinished
                updateTimer()
            }
//ket thuc hoat dong
            override fun onFinish() {
                val newvalue = Integer.valueOf(buttonvalue) + 1

                if (newvalue <= 7) {
                    val intent = Intent(this@TrirdActivity2, TrirdActivity2::class.java)
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

//dem thoi gian tap
    private fun updateTimer() {
        val minutes = MTTimelefttimills / 60000
        val second = (MTTimelefttimills % 60000)/1000

        var TimeLeftText = ""
        if (minutes < 10)
            TimeLeftText = ""
        TimeLeftText = TimeLeftText + minutes + ":"
        if (second < 10)
            TimeLeftText = ""
        TimeLeftText +=second
        btime.setText(TimeLeftText)


    }
    public fun OnBackPressed() {
        super.onBackPressed()
    }
}