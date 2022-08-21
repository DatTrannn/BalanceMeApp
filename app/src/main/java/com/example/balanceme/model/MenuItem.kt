package com.example.balanceme.model


import com.google.gson.annotations.SerializedName

data class MenuItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("imageType")
    val imageType: String,
    @SerializedName("readableServingSize")
    val readableServingSize: String?,
    @SerializedName("restaurantChain")
    val restaurantChain: String,
    @SerializedName("servingSize")
    val servingSize: String?,
    @SerializedName("servings")
    val servings: Servings,
    @SerializedName("title")
    val title: String
)