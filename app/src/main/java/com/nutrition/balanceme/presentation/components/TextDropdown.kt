package com.nutrition.balanceme.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import coil.compose.rememberImagePainter
import com.nutrition.balanceme.domain.models.Item

@Composable
fun TextDropdown(label: String, items: List<Item>, onClick: (Item) -> Unit, onChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val state = remember { TextFieldState<String>(validators = listOf(Required())) }

    fun select(value: Item){
        state.text = ""
        onClick(value)
        expanded = false
    }

    fun onChanged(){
        onChange(state.text)
        expanded = true
    }

    Box {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = false),
            modifier = Modifier.fillMaxWidth(.95f).background(colors.background),
        ) {
            items.forEach {
                DropdownMenuItem(onClick = { select(it) }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = rememberImagePainter(it.image),
                            contentDescription = "${it.name} option",
                            modifier = Modifier.size(35.dp),
                        )
                        SmallSpace()
                        Text(text = it.name, style = typography.body1)
                    }
                }
            }
        }
        OutlinedInput(
            state = state,
            label = label,
            type = KeyboardType.Text,
            onChanged = { onChanged() },
            modifier = Modifier.onFocusChanged { expanded = false },
        )
    }
}