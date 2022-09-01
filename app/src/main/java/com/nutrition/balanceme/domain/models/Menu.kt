package com.nutrition.balanceme.domain.models


import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("expires")
    val expires: Long,
    @SerializedName("isStale")
    val isStale: Boolean,
    @SerializedName("menuItems")
    val menuItems: List<MenuItem>,
    @SerializedName("number")
    val number: Int,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("processingTimeMs")
    val processingTimeMs: Int,
    @SerializedName("totalMenuItems")
    val totalMenuItems: Int,
    @SerializedName("type")
    val type: String
)