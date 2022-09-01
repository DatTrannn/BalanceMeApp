package com.nutrition.balanceme.data.repositories

import com.nutrition.balanceme.data.local.RecipesDao
import com.nutrition.balanceme.data.local.UserDao
import com.nutrition.balanceme.data.remote.RecipesService
import com.nutrition.balanceme.data.remote.UsersService
import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.domain.models.Recipe
import com.nutrition.balanceme.domain.repositories.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FeedRepositoryImpl @Inject constructor(
    val userDao: UserDao,
    val recipesDao: RecipesDao,
    val usersService: UsersService,
    val recipesService: RecipesService,
): FeedRepository {

    override suspend fun refreshData(): String {
        val chefs = usersService.getChefs()
        userDao.addProfile(*chefs.toTypedArray())

        val recipes = recipesService.getRandomRecipes()
        recipesDao.addRecipe(*recipes.toTypedArray())

        return "refresh done"
    }

    override suspend fun getChefs(): List<Profile> {
        val data = userDao.getChefs()
        return data.ifEmpty {
            val netChefs = usersService.getChefs()
            userDao.addProfile(*netChefs.toTypedArray())
            netChefs
        }
    }

    override fun getProfile(): Flow<Profile> {
        return userDao.getProfile()
    }

    override suspend fun getFeaturedRecipes(): List<Recipe> {
        val recipes = recipesDao.getRandomRecipes()
        return recipes.ifEmpty {
            val netRecipes = recipesService.getRandomRecipes()
            recipesDao.addRecipe(*netRecipes.toTypedArray())
            netRecipes
        }
    }

    override suspend fun getRecipesByCategory(categories: List<String>): List<Recipe> {
        val recipes = recipesDao.getRandomRecipes().filter {  hasCategories(it, categories) }
        return recipes.ifEmpty {
            val netRecipes = recipesService.getRandomRecipes()
            recipesDao.addRecipe(*netRecipes.toTypedArray())
            netRecipes.filter {  hasCategories(it, categories) }
        }
    }

    private fun hasCategories(recipe: Recipe, categories: List<String>): Boolean {
        return recipe.categories.any { categories.contains(it) }
    }

}