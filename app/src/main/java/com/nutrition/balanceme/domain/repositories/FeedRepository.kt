package com.nutrition.balanceme.domain.repositories

import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.domain.models.Recipe
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    fun getProfile(): Flow<Profile>

    suspend fun refreshData(): String

    suspend fun getChefs(): List<Profile>

    suspend fun getFeaturedRecipes(): List<Recipe>

    suspend fun getRecipesByCategory(categories: List<String>): List<Recipe>
}

