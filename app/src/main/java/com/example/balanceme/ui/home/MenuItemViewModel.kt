package com.example.balanceme.ui.home

import android.app.DownloadManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.balanceme.model.MenuApi
import com.example.balanceme.model.MenuItem
import kotlinx.coroutines.launch

class MenuItemViewModel: ViewModel() {
    var menuItemListResponse: List<MenuItem> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
    val query: String by mutableStateOf("burger")

    init {
        getMenuItemList("burger")
    }

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