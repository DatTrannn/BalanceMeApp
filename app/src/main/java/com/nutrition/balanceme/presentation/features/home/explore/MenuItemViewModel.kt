package com.nutrition.balanceme.presentation.features.home.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrition.balanceme.domain.models.MenuApi
import com.nutrition.balanceme.domain.models.MenuItem
import kotlinx.coroutines.launch

class MenuItemViewModel : ViewModel() {

    var menuItemListResponse: List<MenuItem> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
    var query: String by mutableStateOf("")

    fun getMenuItemList(query: String) {
        viewModelScope.launch {
            val apiService = MenuApi.getInstance()
            try {
                val menuItemList = apiService.getMenu(query).menuItems
                menuItemListResponse = menuItemList
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}