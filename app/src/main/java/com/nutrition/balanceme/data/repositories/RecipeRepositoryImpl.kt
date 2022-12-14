package com.nutrition.balanceme.data.repositories

import com.nutrition.balanceme.data.local.RecipesDao
import com.nutrition.balanceme.data.local.UserDao
import com.nutrition.balanceme.data.remote.RecipesService
import com.nutrition.balanceme.data.remote.UsersService
import com.nutrition.balanceme.domain.models.CompleteRecipe
import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.domain.models.Recipe
import com.nutrition.balanceme.domain.repositories.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    val userDao: UserDao,
    val recipesDao: RecipesDao,
    val usersService: UsersService,
    val recipesService: RecipesService
): RecipeRepository {

    override fun getProfile(): Flow<Profile> {
        return userDao.getProfile()
    }

    override suspend fun getRecipe(id: String): CompleteRecipe {
        val data = recipesDao.getRecipeById(id)
        return if (data == null){
            val netData = recipesService.getRecipe(id)
            recipesDao.addRecipe(netData)
            populateRecipe(netData)
        } else populateRecipe(data)
    }

    override suspend fun updateProfile(profile: Profile) {
        val update = usersService.updateProfile(profile)
        update.current = true

        userDao.addProfile(update)
    }

    private suspend fun populateRecipe(recipe: Recipe): CompleteRecipe {

        val equipment = recipe.equipment.map { recipesDao.getItemById(it) }
        val ingredients = recipe.ingredients.map { recipesDao.getItemById(it) }

        return CompleteRecipe(
            id = recipe._id,
            user = recipe.user,
            name = recipe.name,
            time = recipe.time,
            image = recipe.image,
            steps = recipe.steps,
            equipment = equipment,
            ingredients = ingredients,
            categories = recipe.categories,
            description = recipe.description,
        )
    }

}