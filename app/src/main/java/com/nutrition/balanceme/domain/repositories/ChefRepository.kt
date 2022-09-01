package com.nutrition.balanceme.domain.repositories

import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.domain.models.Recipe

interface ChefRepository {

    suspend fun getChef(id: String): Profile

    suspend fun getRemoteRecipes(id: String): List<Recipe>
}