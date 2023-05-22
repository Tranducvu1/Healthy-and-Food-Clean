package com.example.healthyandfoodclean

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar

class SecondActivity2 : AppCompatActivity() {

    var newArray: IntArray = intArrayOf()
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

//tao mang lay danh sach hoat dong
        newArray = intArrayOf(
            R.id.boat_pose,
            R.id.plankji_pose,
            R.id.alternativetouch_pose,
            R.id.crunches_pose,
            R.id.legcrunches_pose,
            R.id.legup_pose,
            R.id.sitipji_pose,
            R.id.pauseji_pose
        )
    }
    //nhan gia tri /kiem tra id trung khop anh xa
    fun ImagebuttonClicked(view: View) {
        for (i in newArray.indices) {
            if (view.id == newArray[i]) {
                val value = i + 1
                Log.i("First", value.toString())
                startActivity(
                    Intent(
                        this,
                        TrirdActivity::class.java
                    ).apply {
                        putExtra("value", value.toString())
                    }
                )
            }
        }
    }

}