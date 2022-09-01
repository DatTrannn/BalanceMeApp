package com.nutrition.balanceme.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SearchData(
    val name: String,
    val categories: List<String>
)
