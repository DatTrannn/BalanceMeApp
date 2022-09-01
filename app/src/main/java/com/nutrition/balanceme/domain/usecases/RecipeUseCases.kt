package com.nutrition.balanceme.domain.usecases

import com.nutrition.balanceme.domain.models.CompleteRecipe
import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.domain.repositories.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeUseCases @Inject constructor(private val recipeRepository: RecipeRepository) {
    fun getProfile(): Flow<Profile> {
        return recipeRepository.getProfile()
    }

    suspend fun getRecipe(id: String): CompleteRecipe {
        return recipeRepository.getRecipe(id)
    }

    suspend fun updateProfile(profile: Profile) {
        recipeRepository.updateProfile(profile)
    }

}