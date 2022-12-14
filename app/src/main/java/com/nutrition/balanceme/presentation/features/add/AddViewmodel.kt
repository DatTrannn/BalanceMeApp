package com.nutrition.balanceme.presentation.features.add

import android.net.Uri
import androidx.lifecycle.*
import com.dsc.form_builder.FormState
import com.dsc.form_builder.SelectState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators.Custom
import com.dsc.form_builder.Validators.Required
import com.nutrition.balanceme.domain.models.Item
import com.nutrition.balanceme.data.models.RecipeDTO
import com.nutrition.balanceme.data.remote.UploadState
import com.nutrition.balanceme.domain.models.Recipe
import com.nutrition.balanceme.domain.repositories.AddRepository
import com.nutrition.balanceme.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AddViewmodel @Inject constructor(private val repository: AddRepository) : ViewModel() {

    val progress: LiveData<UploadState> = repository.progress

    private val _path: MutableLiveData<Uri> = MutableLiveData()
    fun setUri(uri: Uri) = uri.also { _path.value = it }
    val path: LiveData<Uri> = _path

    private val _result = MutableLiveData<Result<Recipe>>()
    val result: LiveData<Result<Recipe>> = _result

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val handler = CoroutineExceptionHandler { _, exception ->
        _result.value = Result.Error(exception.parse())
    }

    val formState = FormState(
        fields = listOf(
            TextFieldState(name = "image"),
            TextFieldState(
                name = "name",
                validators = listOf(Required()),
            ),
            TextFieldState(
                name = "description",
                validators = listOf(Required()),
            ),
            TextFieldState(
                name = "time",
                initial = "10",
                transform = { "$it mins"},
                validators = listOf(Required()),
            ),
            SelectState(
                name = "steps",
                validators = listOf(
                    Required(message = "You need to add at least one instruction"),
                    Custom(function = {
                        val items = it as List<String>
                        items.all { i -> i.isNotEmpty() }
                    }, message = "Fill in all the steps")
                ),
            ),
            SelectState(
                name = "equipment",
                validators = listOf(Required(message = "You need to add at least one equipment")),
            ),
            SelectState(
                name = "categories",
                validators = listOf(Required(message = "You need to select at least one category")),
            ),
            SelectState(
                name = "ingredients",
                validators = listOf(Required(message = "You need to add at least one ingredient")),
            )
        )
    )

    fun getItems(name: String, type: String): List<Item> {
        return runBlocking { repository.getItems(name, type) }
    }

    fun uploadImage() {
        if (formState.validate()){
            viewModelScope.launch {
                _loading.value = true
                val name = RandomIdGenerator.getRandom()
                val user = repository.profile.first().id
                val dir = "Foodies/recipes/$user/$name"
                repository.uploadImage(dir, _path.value!!)
            }
        }
    }

    fun uploadRecipe(recipeDTO: RecipeDTO) {
        val recipe = formState.getData(RecipeDTO::class)
        viewModelScope.launch(handler) {
            val result = repository.uploadRecipe(recipe)
            _loading.value = false
            _result.value = Result.Success(result)
        }
    }
}