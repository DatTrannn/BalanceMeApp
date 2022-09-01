package com.nutrition.balanceme.data.models

import kotlinx.serialization.Serializable

@Serializable
data class IngredientsSearch(val ingredients: List<String>)
