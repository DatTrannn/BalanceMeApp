package com.nutrition.balanceme.presentation.components

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

open class TextFieldState<T>(
    initial: String = "",
    val name: String = "",
    val type: Any = String,
    val validators: List<Validators>,
    val onChanged: (String) -> Unit = {},
    val transform: ((String) -> T)? = null,
){

    var text: String by mutableStateOf(initial)
    var message: String by mutableStateOf("")
    var hasError: Boolean by mutableStateOf(false)

    fun change(value: String){
        hideError()
        text = value
        onChanged(value)
    }

    fun showError(error: String){
        hasError = true
        message = error
    }

    fun hideError(){
        message = ""
        hasError = false
    }

    fun validate(): Boolean {
        val validations =  validators.map {
            when (it){
                is Email -> validateEmail()
                is Required -> validateRequired()
            }
        }
        return validations.all { it }
    }

    private fun validateEmail(): Boolean {
        val valid = Patterns.EMAIL_ADDRESS.matcher(text).matches()
        if (!valid) showError("invalid email address")
        return valid
    }

    private fun validateRequired(): Boolean {
        val valid = text.isNotEmpty()
        if (!valid) showError("this field is required")
        return valid
    }
}

