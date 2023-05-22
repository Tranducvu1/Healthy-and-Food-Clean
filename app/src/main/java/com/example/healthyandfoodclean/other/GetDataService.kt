package com.example.healthyandfoodclean.other





import com.example.healthyandfoodclean.entities.Category
import com.example.healthyandfoodclean.entities.Meal
import com.example.healthyandfoodclean.entities.MealResponse


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
//lay du lieu tu link ket http/goi lai gia tri
interface GetDataService {
    //get category
    @GET("categories.php")
    fun getCategoryList(): Call<Category>
//get Meal
    @GET("filter.php")
    fun getMealList(@Query("c") category: String):Call<Meal>
//get Mealreponsse
    @GET("lookup.php")
    fun getSpecificItem(@Query("i") id: String):Call<MealResponse>


}