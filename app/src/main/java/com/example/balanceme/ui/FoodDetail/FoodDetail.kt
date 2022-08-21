package com.example.balanceme.ui.FoodDetail

import android.icu.text.CaseMap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.balanceme.ui.theme.RichBlack_Light
import retrofit2.http.Body

@Composable
fun FoodDetail(
    foodId: Long,
    upPress: () -> Unit
) {
//    val food = remember(foodId) { FoodRepo.getfood(foodId) }
//    val related = remember(foodId) { FoodRepo.getRelated(foodId) }
//
//    Box(Modifier.fillMaxSize()) {
//        val scroll = rememberScrollState(0)
//        Header()
//        Body(related, scroll)
//        CaseMap.Title(food) { scroll.value }
//        Image(food.imageUrl) { scroll.value }
//        Up(upPress)
//        CartBottomBar(modifier = Modifier.align(Alignment.BottomCenter))
//    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(listOf(RichBlack_Light, Color.White)))
    )
}