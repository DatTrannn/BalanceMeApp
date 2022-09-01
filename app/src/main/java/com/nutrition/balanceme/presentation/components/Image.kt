package com.nutrition.balanceme.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter.State
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.nutrition.balanceme.R


@Composable
@ExperimentalCoilApi
fun NetImage(modifier: Modifier = Modifier, url: String, description: String = ""){
    val painter =
        rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(data = url)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
            }).build())

    when (painter.state){
        is State.Loading -> {
            Box(
                content = {},
                modifier = modifier.placeholder(
                    visible = true,
                    color = colors.primaryVariant.copy(alpha = .3f),
                    highlight = PlaceholderHighlight.fade()
                )
            )
        }
        else -> {
//            Image(
//                contentDescription = "error",
//                modifier = Modifier.width(10.dp),
//                painter = painterResource(id = R.drawable.error)
//            )
        }
    }

    Image(
        painter = painter,
        modifier = modifier,
        contentScale = ContentScale.Crop,
        contentDescription = description,
    )

}