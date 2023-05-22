package com.example.healthyandfoodclean

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

private lateinit var buttonHADBack:Button
private lateinit var imageViewHAD:ImageView
private lateinit var textViewHADtitle:TextView
class HealthyArticleDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthy_article_detail)
        val btnBack = findViewById<Button>(R.id.buttonHADBack)
        val tv1 = findViewById<TextView>(R.id.textViewHADtitle)
        val img = findViewById<ImageView>(R.id.imageViewHAD)
        val intent = intent
        tv1.text = intent.getStringExtra("text1")

        val bundle = intent.extras
        if(bundle !=null){
            val resID = bundle.getInt("text2")
            img.setImageResource(resID)
        }
        btnBack.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HealthyArticlesActivity::class.java
                )
            )
        }
    }
}