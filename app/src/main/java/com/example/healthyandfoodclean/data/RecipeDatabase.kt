package com.example.healthyandfoodclean.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.healthyandfoodclean.converter.MealListConverter
import com.example.healthyandfoodclean.entities.*

import com.example.healthyandfoodclean.entities.converter.CategoryListConverter

//khoi tao rooom database
@Database(entities = [Recipes::class, CategoryItems::class, Category::class, Meal::class, MealsItems::class], version = 1, exportSchema = false)
@TypeConverters(CategoryListConverter::class, MealListConverter::class)
abstract class RecipeDatabase: RoomDatabase() {
    companion object{
        var recipesDatabase:RecipeDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): RecipeDatabase{
            if (recipesDatabase == null){
                recipesDatabase = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "recipe.db"
                ).fallbackToDestructiveMigration().build()
            }
            return recipesDatabase!!
        }
    }

    abstract fun recipeDao():RecipeDao
}