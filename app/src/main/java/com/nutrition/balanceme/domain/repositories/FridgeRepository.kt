package com.nutrition.balanceme.domain.repositories

import com.nutrition.balanceme.data.local.RecipesDao
import com.nutrition.balanceme.data.remote.RecipesService
import com.nutrition.balanceme.data.models.IngredientsSearch
import com.nutrition.balanceme.domain.models.Item
import com.nutrition.balanceme.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FridgeRepository {
    val ingredients: Flow<List<Item>>

    suspend fun getSuggestions(items: List<Item>): List<Recipe>
}

class FridgeRepositoryImpl @Inject constructor(val recipesDao: RecipesDao, val api: RecipesService): FridgeRepository {
    override val ingredients: Flow<List<Item>>
        get() = recipesDao.getIngredients()

    override suspend fun getSuggestions(items: List<Item>): List<Recipe> {
        val search = IngredientsSearch(ingredients = items.map { it._id })
        return api.getSuggestions(search)
    }

}