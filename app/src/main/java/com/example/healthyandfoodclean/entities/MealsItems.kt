package com.example.healthyandfoodclean.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
//khoi tao database cho Meal
@Entity(tableName = "MealItems")
data class MealsItems(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name = "categoryName")
    val categoryName: String,
    @SerializedName("idMeal")
    val idMeal: String, // 52959
    @SerializedName("strMeal")
    val strMeal: String, // Baked salmon with fennel & tomatoes
    @SerializedName("strMealThumb")
    val strMealThumb: String // https://www.themealdb.com/images/media/meals/1548772327.jpg
)