package com.example.healthyandfoodclean

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.healthyandfoodclean.entities.MealDetail
import com.example.healthyandfoodclean.fragment.EatClean2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class EatClean : AppCompatActivity() {
    private lateinit var mealListView: ListView
    private lateinit var randomButton: Button
    private lateinit var mealAdapter: ArrayAdapter<MealDetail>
    private lateinit var mealList: MutableList<MealDetail>
    private lateinit var logout:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eat_clean2)
// Trong phương thức onCreate
        logout = findViewById(R.id.LogOut)
        mealListView = findViewById(R.id.mealListView)
        randomButton = findViewById(R.id.randomButton)
        mealList = mutableListOf()
        mealAdapter = ArrayAdapter(this, R.layout.list_meal, R.id.textViewMealName, mealList)
        mealListView.adapter = mealAdapter
        logout.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HomeActivity::class.java
                )
            )
        }
        mealListView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedMeal = mealList[position]
                val intent = Intent(this, EatClean2::class.java)
                intent.putExtra("mealName", selectedMeal.mealName)
                intent.putExtra("instructions", selectedMeal.instructions)
                startActivity(intent)
            }

        randomButton.setOnClickListener {
            showRandomMeal()
        }

        fetchMealSchedule()
    }
//get API lay thong tin json
    private fun fetchMealSchedule() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://www.themealdb.com/api/json/v1/1/search.php?s=chicken")
            .build()

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                val mealSchedule = parseMealSchedule(responseData)
                runOnUiThread {
                    mealList.clear()
                    mealList.addAll(mealSchedule)
                    mealAdapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }
    }
//phan tich api lay thông tin json
    private fun parseMealSchedule(responseData: String?): List<MealDetail> {
        val mealSchedule = mutableListOf<MealDetail>()
        try {
            val jsonObject = JSONObject(responseData)
            val meals = jsonObject.getJSONArray("meals")

            for (i in 0 until meals.length()) {
                val meal = meals.getJSONObject(i)
                val mealName = meal.getString("strMeal")
                val date = meal.optString("dateModified", "Unknown Date")
                val instructions = meal.getString("strInstructions")

                val mealDetail = MealDetail(mealName, date, instructions)
                mealSchedule.add(mealDetail)
            }

        } catch (e: JSONException) {
            Log.e(TAG, "JSON parsing error: ${e.message}")
            val errorMeal = MealDetail("Error parsing meal schedule.", "", "")
            mealSchedule.add(errorMeal)
        }

        return mealSchedule
    }
//sap xep mon an ngau nhien
    private fun showRandomMeal() {
        if (mealList.isNotEmpty()) {
            val randomIndex = Random().nextInt(mealList.size)
            val selectedMeal = mealList[randomIndex]
            val intent = Intent(this, EatClean2::class.java)
            intent.putExtra("mealName", selectedMeal.mealName)
            intent.putExtra("instructions", selectedMeal.instructions)
            startActivity(intent)
        }
    }
}
