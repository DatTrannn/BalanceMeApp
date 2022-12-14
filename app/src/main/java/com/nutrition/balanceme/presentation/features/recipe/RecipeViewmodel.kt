package com.nutrition.balanceme.presentation.features.recipe

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrition.balanceme.domain.models.CompleteRecipe
import com.nutrition.balanceme.domain.repositories.RecipeRepository
import com.nutrition.balanceme.domain.usecases.RecipeUseCases
import com.nutrition.balanceme.util.parse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeViewmodel @Inject constructor(val repository: RecipeRepository,private val recipeUseCases: RecipeUseCases): ViewModel() {

    val profile = recipeUseCases.getProfile()

    private val _favorite: MutableState<Boolean> = mutableStateOf(false)
    val favorite: State<Boolean> = _favorite

    private val _recipe: MutableState<CompleteRecipe?> = mutableStateOf(null)
    val recipe: State<CompleteRecipe?> = _recipe

    private val _error: MutableState<String?> = mutableStateOf(null)
    val error: State<String?> = _error

    private val handler = CoroutineExceptionHandler { _, exception ->
        _error.value = exception.parse()
    }

    fun checkFavorite(id: String){
        viewModelScope.launch(handler) {
            _favorite.value = profile.first().favorites.contains(id)
        }
    }

    fun getRecipe(id: String){
        viewModelScope.launch(handler) {
            _recipe.value = recipeUseCases.getRecipe(id)
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch(handler) {
            val profile = profile.first()
            val recipe = _recipe.value!!.id

            if (_favorite.value) {
                profile.favorites = profile.favorites - recipe
                _favorite.value = false
            } else {
                profile.favorites = profile.favorites + recipe
                _favorite.value = true
            }

            repository.updateProfile(profile)
        }
    }
}