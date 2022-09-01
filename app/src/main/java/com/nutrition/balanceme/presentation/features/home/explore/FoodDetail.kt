package com.nutrition.balanceme.presentation.features.home.explore

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.util.lerp
import coil.compose.rememberAsyncImagePainter
import com.nutrition.balanceme.R
import com.nutrition.balanceme.domain.models.MenuItem
import com.nutrition.balanceme.presentation.components.BalanceMeDivider
import com.nutrition.balanceme.presentation.theme.BalanceMeTheme
import com.nutrition.balanceme.presentation.theme.Gainsboro
import com.nutrition.balanceme.presentation.theme.RichBlack_Light
import com.nutrition.balanceme.util.mirroringBackIcon
import kotlin.math.max
import kotlin.math.min

private val BottomBarHeight = 56.dp
private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
fun FoodDetail(
    menuItem: MenuItem,
) {
    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        Body(scroll, menuItem.id)
        Title(menuItem) { scroll.value }
        Image(menuItem.image) { scroll.value }
    }
}

@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(Brush.horizontalGradient(listOf(RichBlack_Light, Gainsboro)))
    )
}

@Composable
private fun Title(menuItem: MenuItem, scrollProvider: () -> Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
            .background(color = Color.White)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = menuItem.title.replace("+", " "),
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary,
            modifier = HzPadding
        )
        Text(
            text = menuItem.readableServingSize?.replace("+", " ") ?: "",
            style = MaterialTheme.typography.subtitle2,
            fontSize = 20.sp,
            color = RichBlack_Light,
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = menuItem.restaurantChain?.replace("+", " ") ?: "",
            style = MaterialTheme.typography.h6,
            color = RichBlack_Light,
            modifier = HzPadding
        )
        Spacer(Modifier.height(8.dp))
        BalanceMeDivider()
    }
}

@Composable
private fun Image(
    imageUrl: String,
    scrollProvider: () -> Int,
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }
    val imagePainter = rememberAsyncImagePainter(model = imageUrl)

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.then(Modifier.statusBarsPadding())
    ) {
        MenuItemImage(
            imagePainter = imagePainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            constraints.maxWidth - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}

@Composable
private fun Body(
    scroll: ScrollState,
    itemId: Int,
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(MinTitleOffset)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            val link =
                "https://api.spoonacular.com/food/menuItems/${itemId}/nutritionLabel.png??showOptionalNutrients=false&showZeroValues=false&showIngredients=false&apiKey=644c5ce1ecf84094b68b90350ebedc6e"
            val informationImagePainter =
                rememberAsyncImagePainter(model = link)
            Surface(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))
                    Image(
                        painter = informationImagePainter,
                        contentDescription = "Nutrition Detail",
                        modifier = HzPadding
                            .height(500.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.FillHeight,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun testBody() {
    BalanceMeTheme {
        Body(scroll = rememberScrollState(), itemId = 364691)
    }
}
