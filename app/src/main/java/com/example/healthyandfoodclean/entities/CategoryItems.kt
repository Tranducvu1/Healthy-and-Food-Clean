package com.example.healthyandfoodclean.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

//khoi tao database category
@Entity(tableName = "CategoryItems")
data class CategoryItems(

    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @SerializedName("idCategory")
    val idCategory: String, // 1
    @SerializedName("strCategory")
    val strCategory: String, // Beef
    @SerializedName("strCategoryDescription")
    val strCategoryDescription: String, // Beef is the culinary name for meat from cattle, particularly skeletal muscle. Humans have been eating beef since prehistoric times.[1] Beef is a source of high-quality protein and essential nutrients.[2]
    @SerializedName("strCategoryThumb")
    val strCategoryThumb: String // https://www.themealdb.com/images/category/beef.png
)