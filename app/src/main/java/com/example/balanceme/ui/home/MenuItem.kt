package com.example.balanceme.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.balanceme.model.MenuItem
import com.example.balanceme.model.Servings
import com.example.balanceme.ui.components.BalanceMeSurface
import com.example.balanceme.ui.theme.BalanceMeTheme
import com.example.balanceme.ui.theme.RichBlack_Dark

@Composable
fun MenuItem(
    onMenuItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    menuItemViewModel: MenuItemViewModel,
) {
    BalanceMeSurface(modifier = modifier.fillMaxSize()) {
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
                    MenuItemImageCard(menuItem = menuItem, modifier, onMenuItemClick)
                }
            }
        }
    }
}

@Composable
fun MenuItemImageCard(menuItem: MenuItem, modifier: Modifier, onMenuItemClick: (Int) -> Unit) {
    val imagerPainter = rememberAsyncImagePainter(menuItem.image)

    Card(
        modifier = modifier
            .size(
                width = 50.dp,
                height = 250.dp
            )
            .padding(bottom = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable(onClick = { onMenuItemClick(menuItem.id) })
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
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = RichBlack_Dark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = menuItem.readableServingSize ?: "",
                style = MaterialTheme.typography.body1,
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
    BalanceMeSurface(
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
    onSearch: (String) -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
            val query = menuItemViewModel.query
            val focusManager = LocalFocusManager.current
            TextField(
                value = query,
                onValueChange = { newValue: String ->
                    menuItemViewModel.getMenuItemList(newValue)
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
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
            )
    }
}

@Preview("default")
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", fontScale = 2f)
@Composable
fun HomePreview() {
    BalanceMeTheme {
        val item = MenuItem(
            370299,
            "https://spoonacular.com/menuItemImages/hamburger.jpg",
            "jpg",
            "De Dutch Pannekoek House",
            "417grams",
            "417 grams",
            Servings(1, 417, "grams"),
            "Meek Myrtle Burger, Chicken"
        )
        MenuItemImageCard(menuItem = item, modifier = Modifier, onMenuItemClick = {})
        MenuItem(onMenuItemClick = {}, menuItemViewModel = MenuItemViewModel())
    }
}