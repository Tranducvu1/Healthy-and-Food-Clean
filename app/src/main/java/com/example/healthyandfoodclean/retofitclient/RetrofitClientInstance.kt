package com.example.healthyandfoodclean.retofitclient


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//resapi retrofit nhan lien ket json
class RetrofitClientInstance {
    companion object{
        val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"
        private var retrofit: Retrofit? = null
        val retrofitInstance: Retrofit?
        //lay thong tin ,khoi tao va thuc hien chuyen doi lien ket
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retrofit
            }
    }

}