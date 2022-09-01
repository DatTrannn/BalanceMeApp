package com.nutrition.balanceme.presentation.features.add

import androidx.compose.runtime.*
import com.nutrition.balanceme.presentation.components.TextDropdown
import com.nutrition.balanceme.domain.models.Item

@Composable
fun Items(viewmodel: AddViewmodel, type: String, onClick: (Item) -> Unit) {

    var items by remember { mutableStateOf(viewmodel.getItems("", type)) }

    TextDropdown(
        label = type,
        items = items,
        onClick = onClick,
        onChange = { items = viewmodel.getItems(it, type) }
    )
}