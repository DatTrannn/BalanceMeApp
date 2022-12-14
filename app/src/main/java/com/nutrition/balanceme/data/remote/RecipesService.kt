package com.nutrition.balanceme.data.remote

import com.nutrition.balanceme.data.models.IngredientsSearch
import com.nutrition.balanceme.data.models.RecipeDTO
import com.nutrition.balanceme.data.models.SearchData
import com.nutrition.balanceme.domain.models.*
import com.nutrition.balanceme.util.BASE_URL
import io.ktor.client.*
import io.ktor.client.request.*
import javax.inject.Inject

class RecipesService @Inject constructor(val client: HttpClient) {

    suspend fun getItems(update: String?): List<Item>{
        val url = "$BASE_URL/api/items"
        return client.get(url){ parameter("update", update)}
    }

    suspend fun getRandomRecipes(): List<Recipe> {
        val url = "$BASE_URL/api/recipes"
        return client.get(url)
    }

    suspend fun getRecipe(id: String): Recipe {
        val url = "$BASE_URL/api/recipes/$id"
        return client.get(url)
    }

    suspend fun getUserRecipes(id: String): List<Recipe>{
        val url = "$BASE_URL/api/recipes/user/$id"
        return client.get(url)
    }

    suspend fun uploadRecipe(recipeDTO: RecipeDTO): Recipe{
        val url = "$BASE_URL/api/recipes"
        return client.post{url(url); body = recipeDTO}
    }

    suspend fun searchRecipe(search: SearchData): List<Recipe> {
        val url = "$BASE_URL/api/recipes/search"
        return client.post { url(url); body = search }
    }

    suspend fun getSuggestions(search: IngredientsSearch): List<Recipe>{
        val url = "$BASE_URL/api/recipes/suggestions"
        return client.post { url(url); body = search }
    }
}