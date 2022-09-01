package com.nutrition.balanceme.presentation.features.home.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.google.gson.Gson
import com.nutrition.balanceme.domain.models.MenuItem
import com.nutrition.balanceme.presentation.features.home.Screen
import com.nutrition.balanceme.presentation.theme.RichBlack_Dark
import java.net.URLEncoder

@Composable
fun Explore(
    menuItemViewModel: MenuItemViewModel,
    controller: NavHostController,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(text = menuItemViewModel.errorMessage)
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                menuItemViewModel = menuItemViewModel
            )
            Divider()
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
            ) {
                if (menuItemViewModel.menuItemListResponse.isEmpty()) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(align = Alignment.Center)
                        )
                    }
                }
                items(menuItemViewModel.menuItemListResponse) { menuItem: MenuItem ->
                    MenuItemImageCard(menuItem = menuItem, controller)
                }
            }
        }
    }
}

fun NavGraphBuilder.detailNavHost(
    navController: NavHostController,
) {
    navigation(
        startDestination = "/menuItem",
        route = Screen.Explore.route
    ) {
        composable("/menuItem") {
            val menuItemViewModel = viewModel<MenuItemViewModel>()
            Explore(
                menuItemViewModel,
                navController
            )
            menuItemViewModel.getMenuItemList("burger")
        }
        composable(ROUTE_MENU_ITEM_DETAILS) { backStackEntry ->
            val menuItemJson = backStackEntry.arguments?.getString("menuItem")
            val gson = Gson()
            val menuItemObject = gson.fromJson(menuItemJson, MenuItem::class.java)
            FoodDetail(menuItemObject)
        }
    }
}

fun String.urlEncode(): String = URLEncoder.encode(this, "utf-8")

const val ROUTE_MENU_ITEM_DETAILS = "/menuItem/menuItemDetail/menuItem={menuItem}"

@Composable
fun MenuItemImageCard(menuItem: MenuItem, navController: NavHostController) {
    val imagerPainter = rememberAsyncImagePainter(menuItem.image)
    Card(
        modifier = Modifier
            .size(
                width = 200.dp,
                height = 250.dp
            )
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = {
                    val gson = Gson()
                    val menuItemJson = gson
                        .toJson(menuItem)
                        .urlEncode()
                    navController.navigate(ROUTE_MENU_ITEM_DETAILS.replace("{menuItem}",
                        menuItemJson))
                })
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                )
                MenuItemImage(
                    imagePainter = imagerPainter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.BottomCenter)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = menuItem.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = typography.h6,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = RichBlack_Dark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = menuItem.readableServingSize ?: "",
                style = typography.body1,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = RichBlack_Dark
            )
        }
    }
}

@Composable
fun MenuItemImage(
    imagePainter: AsyncImagePainter,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp,
) {
    Surface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        Image(
            painter = imagePainter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    menuItemViewModel: MenuItemViewModel,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        val focusManager = LocalFocusManager.current
        TextField(
            value = menuItemViewModel.query,
            onValueChange = { newValue: String ->
                menuItemViewModel.query = newValue
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            label = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, "")
            },
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
                menuItemViewModel.getMenuItemList(menuItemViewModel.query)
            }),
            textStyle = TextStyle(color = colors.onSurface),
            colors = TextFieldDefaults.textFieldColors(textColor = colors.onSurface)
        )
    }
}