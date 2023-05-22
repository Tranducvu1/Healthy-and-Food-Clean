package com.example.healthyandfoodclean

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.healthyandfoodclean.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fun loading(){
            val loading = LoadingDialog(this)
            loading.startLoading()
            val handler = Handler()
            handler.postDelayed(object :Runnable{
                override fun run() {
                   loading.isDismiss()
                }

            },5000)
        }
        binding.btnLogin.setOnClickListener {
            loading()
            startActivity(

                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
        }
        binding.btnregis.setOnClickListener {
            loading()
            startActivity(
                Intent(
                    this,
                    RegisterActivity::class.java
                )
            )
        }
    }
}