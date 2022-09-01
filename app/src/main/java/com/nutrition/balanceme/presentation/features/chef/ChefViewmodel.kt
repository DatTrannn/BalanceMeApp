package com.nutrition.balanceme.presentation.features.chef

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrition.balanceme.domain.models.Profile
import com.nutrition.balanceme.domain.models.Recipe
import com.nutrition.balanceme.domain.usecases.ChefUseCases
import com.nutrition.balanceme.util.parse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChefViewmodel @Inject constructor(private val chefUseCases: ChefUseCases): ViewModel() {

    init {
       startCounter()
    }

    private val _chef: MutableState<Profile?> = mutableStateOf(null)
    val chef: State<Profile?> = _chef

    private val _recipes: MutableState<List<Recipe>?> = mutableStateOf(null)
    val recipes: State<List<Recipe>?> = _recipes

    private val _seconds = mutableStateOf(0)
    val seconds: State<Int> = _seconds

    private val _error: MutableState<String?> = mutableStateOf(null)
    val error: State<String?> = _error

    private val handler = CoroutineExceptionHandler { _, exception ->
        _error.value = exception.parse()
    }

    private fun startCounter() {
        viewModelScope.launch {
            delay(2000)
            _seconds.value = 3
        }
    }

    fun getChef(id: String) {
        viewModelScope.launch(handler) {
            _chef.value = chefUseCases.getChef(id)
            _recipes.value = chefUseCases.getRemoteRecipes(id)
        }
    }
}