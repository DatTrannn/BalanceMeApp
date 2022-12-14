package com.nutrition.balanceme.domain.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Recipe(
    @PrimaryKey
    val _id: String,

    @Embedded(prefix = "user_")
    val user: Profile,
    val name: String,
    val time: String,
    val image: String,
    var type: String = "",
    val description: String,
    val steps: List<String>,
    val equipment: List<String>,
    val categories: List<String>,
    val ingredients: List<String>,
)

data class CompleteRecipe(
    val id: String,
    val user: Profile,
    val name: String,
    val time: String,
    val image: String,
    val description: String,
    val steps: List<String>,
    val equipment: List<Item>,
    val categories: List<String>,
    val ingredients: List<Item>,
)