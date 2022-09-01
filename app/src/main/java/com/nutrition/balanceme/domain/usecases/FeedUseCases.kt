package com.nutrition.balanceme.domain.usecases

import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.domain.models.Recipe
import com.nutrition.balanceme.domain.repositories.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedUseCases @Inject constructor(private val feedRepository: FeedRepository) {

    suspend fun refreshData(): String {
        return feedRepository.refreshData()
    }

    suspend fun getChefs(): List<Profile> {
        return feedRepository.getChefs()
    }

    fun getUserProfile(): Flow<Profile> {
        return feedRepository.getProfile()
    }

    suspend fun getFeaturedRecipes(): List<Recipe> {
        return feedRepository.getFeaturedRecipes()
    }

    suspend fun getRecipesByCategory(category: List<String>): List<Recipe> {
        return feedRepository.getRecipesByCategory(category)
    }

}