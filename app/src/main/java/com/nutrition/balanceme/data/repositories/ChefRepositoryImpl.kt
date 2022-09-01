package com.nutrition.balanceme.data.repositories

import com.nutrition.balanceme.data.local.RecipesDao
import com.nutrition.balanceme.data.local.UserDao
import com.nutrition.balanceme.data.remote.RecipesService
import com.nutrition.balanceme.data.remote.UsersService
import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.domain.models.Recipe
import com.nutrition.balanceme.domain.repositories.ChefRepository
import javax.inject.Inject

class ChefRepositoryImpl @Inject constructor(
    val userDao: UserDao,
    val recipesDao: RecipesDao,
    val usersService: UsersService,
    val recipesService: RecipesService,
): ChefRepository {

    override suspend fun getChef(id: String): Profile {
        return userDao.getChef(id) ?: usersService.getProfile(id)
    }

    override suspend fun getRemoteRecipes(id: String): List<Recipe> {
        return recipesService.getUserRecipes(id)
    }
}