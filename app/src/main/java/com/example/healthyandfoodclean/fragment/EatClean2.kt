package com.example.healthyandfoodclean.fragment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.healthyandfoodclean.DrinkWaterActivity
import com.example.healthyandfoodclean.EatClean
import com.example.healthyandfoodclean.R

class EatClean2 : AppCompatActivity() {
    private lateinit var textViewMealName: TextView
    private lateinit var textViewInstructions: TextView
    private lateinit var button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eat_clean3)
        button = findViewById(R.id.button)
        textViewMealName = findViewById(R.id.textViewMealName)
        textViewInstructions = findViewById(R.id.textViewInstructions)

        val mealName = intent.getStringExtra("mealName")
        val instructions = intent.getStringExtra("instructions")

        textViewMealName.text = mealName
        textViewInstructions.text = instructions
        button.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    EatClean::class.java
                )
            )
        }
    }
}
