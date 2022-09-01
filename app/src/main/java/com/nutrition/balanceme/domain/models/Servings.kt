package com.nutrition.balanceme.domain.models


import com.google.gson.annotations.SerializedName

data class Servings(
    @SerializedName("number")
    val number: Int,
    @SerializedName("size")
    val size: Float?,
    @SerializedName("unit")
    val unit: String
)