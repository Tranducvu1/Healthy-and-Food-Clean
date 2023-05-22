package com.example.healthyandfoodclean.converter

import androidx.room.TypeConverter
import com.example.healthyandfoodclean.entities.MealsItems

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
//chuyen doi json meal
class MealListConverter {
    @TypeConverter
    fun fromMealsList(mealsList: List<MealsItems>): String? {
        if (mealsList == null) {
            return null
        } else {
            val gson = Gson()
            val type = object : TypeToken<List<MealsItems>>() {}.type
            return gson.toJson(mealsList, type)
        }
    }

    @TypeConverter
    fun toMealsList(mealsListString: String): List<MealsItems>? {
        if (mealsListString == null) {
            return null
        } else {
            val gson = Gson()
            val type = object : TypeToken<List<MealsItems>>() {}.type
            return gson.fromJson(mealsListString, type)
        }
    }
}
