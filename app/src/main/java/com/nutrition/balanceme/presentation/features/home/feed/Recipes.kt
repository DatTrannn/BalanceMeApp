package com.nutrition.balanceme.presentation.features.home.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.nutrition.balanceme.domain.models.Recipe
import com.nutrition.balanceme.presentation.components.TinySpace
import com.nutrition.balanceme.util.customTabIndicatorOffset
import kotlinx.coroutines.launch


@Composable
@ExperimentalPagerApi
fun Recipes(controller: NavController) {
    val viewmodel: FeedViewmodel = hiltViewModel()

    val titles = viewmodel.titles
    val recipes by remember { viewmodel.recipes }
    val state = rememberPagerState(initialPage = 0)

    Text(
        text = "Recipes",
        modifier = Modifier.padding(10.dp),
        style = typography.h5.copy(color = colors.primary)
    )

    TabRow(
        divider = {},
        backgroundColor = Transparent,
        selectedTabIndex = state.currentPage,
        indicator = { Indicator(state, it) },
        tabs = { titles.forEachIndexed { i, t -> PagerTab(t, state, i) } }
    )

    TinySpace()
    RecipeItems(controller, recipes)
    HorizontalPager(count = titles.size, state = state) {}
}

@Composable
@ExperimentalPagerApi
fun PagerTab(title: String, state: PagerState, index: Int) {
    val current = state.currentPage
    val coroutineScope = rememberCoroutineScope()
    val viewmodel: FeedViewmodel = hiltViewModel()

    fun scroll() {
        viewmodel.getRecipes(index)
        coroutineScope.launch { state.animateScrollToPage(index) }
    }

    Tab(selected = false, onClick = { scroll() }) {
        Text(
            text = title,
            modifier = Modifier.padding(15.dp),
            color = if (current == index) colors.secondary else colors.onSurface,
        )
    }
}

@Composable
@ExperimentalPagerApi
fun Indicator(state: PagerState, positions: List<TabPosition>) {
    TabRowDefaults.Indicator(
        height = 7.5.dp,
        color = colors.secondary,
        modifier = Modifier.customTabIndicatorOffset(positions[state.currentPage])
    )
}

@Composable
fun RecipeItems(controller: NavController, recipes: List<Recipe>) {
    LazyRow {
        items(recipes) { recipe ->
            val arrangement = Arrangement.SpaceBetween

            // create the gradient
            val variant = colors.primaryVariant
            val colors = listOf(Transparent, Transparent, Transparent, variant)
            val gradient = Brush.verticalGradient(colors = colors)

            val cardModifier = Modifier
                .width(200.dp)
                .height(250.dp)
                .padding(10.dp)
            val boxModifier = Modifier
                .fillMaxSize()
                .background(brush = gradient)
                .padding(10.dp)
                .clickable { controller.navigate("/recipe/${recipe._id}") }

            Card(
                elevation = 5.dp,
                modifier = cardModifier,
                content = {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "${recipe.name} image",
                        painter = rememberImagePainter(data = recipe.image),
                    )
                    Box(
                        modifier = boxModifier,
                        content = {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = arrangement
                            ) {
                                TinySpace()
                                Text(
                                    maxLines = 1,
                                    text = recipe.name,
                                    fontWeight = FontWeight.Medium,
                                    overflow = TextOverflow.Ellipsis,
                                    style = typography.h6.copy(color = Color.White)
                                )
                            }
                        }
                    )
                }
            )
        }
    }
}