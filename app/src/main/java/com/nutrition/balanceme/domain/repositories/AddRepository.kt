package com.nutrition.balanceme.domain.repositories

import android.net.Uri
import androidx.lifecycle.LiveData
import com.nutrition.balanceme.data.local.RecipesDao
import com.nutrition.balanceme.data.local.UserDao
import com.nutrition.balanceme.data.remote.RecipesService
import com.nutrition.balanceme.domain.models.Item
import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.data.models.RecipeDTO
import com.nutrition.balanceme.data.remote.Cloudinary
import com.nutrition.balanceme.data.remote.UploadState
import com.nutrition.balanceme.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AddRepository {

    val profile: Flow<Profile>

    val progress: LiveData<UploadState>

    suspend fun uploadImage(dir: String, path: Uri)

    suspend fun getItems(name: String,  type: String): List<Item>

    suspend fun uploadRecipe(recipeDTO: RecipeDTO): Recipe
}

class AddRepositoryImpl @Inject constructor(
    val userDao: UserDao,
    val recipesDao: RecipesDao,
    val cloudinary: Cloudinary,
    val recipesService: RecipesService,
): AddRepository {

    override val profile: Flow<Profile>
        get() = userDao.getProfile()

    override val progress: LiveData<UploadState>
        get() = cloudinary.progress

    override suspend fun uploadImage(dir: String, path: Uri) = cloudinary.uploadImage(dir, path)

    override suspend fun getItems(name: String, type: String): List<Item> {
        return recipesDao.searchItem("%$name%", type)
    }

    override suspend fun uploadRecipe(recipeDTO: RecipeDTO): Recipe {
        cloudinary.clearProgress()
        val result = recipesService.uploadRecipe(recipeDTO)
        recipesDao.addRecipe(result.copy(type = "PERSONAL"))
        return result
    }
}