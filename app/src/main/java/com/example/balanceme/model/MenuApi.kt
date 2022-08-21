package com.example.balanceme.model

import com.example.balanceme.model.ApiConstants.BASE_URL
import com.example.balanceme.model.ApiConstants.apiKey
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MenuApi {

    @GET(value = "/food/menuItems/search?apiKey=$apiKey")
    suspend fun getMenu(
        @Query("query") query: String,
    ): Menu

    @GET("/food/menuItems/{id}/nutritionWidget.png?apiKey=$apiKey")
    suspend fun getNutritionImage(
        @Path("id") id: String,
    )

    companion object {
        var menuApi: MenuApi? = null
        fun getInstance(): MenuApi {
            if (menuApi == null) {
                menuApi = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(MenuApi::class.java)
            }
            return menuApi!!
        }
    }
}