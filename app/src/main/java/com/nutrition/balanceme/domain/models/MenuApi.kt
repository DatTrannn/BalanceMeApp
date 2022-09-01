package com.nutrition.balanceme.domain.models

import com.nutrition.balanceme.util.API_KEY
import com.nutrition.balanceme.util.BASE_URL_SPOONACULAR
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MenuApi {

    @GET(value = "/food/menuItems/search")
    suspend fun getMenu(
        @Query("query") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Menu

    companion object {
        var menuApi: MenuApi? = null
        fun getInstance(): MenuApi {
            if (menuApi == null) {
                menuApi = Retrofit.Builder()
                    .baseUrl(BASE_URL_SPOONACULAR)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(MenuApi::class.java)
            }
            return menuApi!!
        }
    }
}