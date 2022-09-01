package com.nutrition.balanceme.domain.usecases

import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.domain.models.Recipe
import com.nutrition.balanceme.domain.repositories.ChefRepository
import javax.inject.Inject

class ChefUseCases @Inject constructor(private val chefRepository: ChefRepository) {
    suspend fun getChef(id: String): Profile {
        return chefRepository.getChef(id)
    }

    suspend fun getRemoteRecipes(id: String): List<Recipe>{
        return chefRepository.getRemoteRecipes(id)
    }
}