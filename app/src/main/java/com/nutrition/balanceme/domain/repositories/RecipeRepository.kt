package com.nutrition.balanceme.domain.repositories

import com.nutrition.balanceme.domain.models.CompleteRecipe
import com.nutrition.balanceme.domain.models.Profile
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getProfile(): Flow<Profile>

    suspend fun getRecipe(id: String): CompleteRecipe

    suspend fun updateProfile(profile: Profile)
}
