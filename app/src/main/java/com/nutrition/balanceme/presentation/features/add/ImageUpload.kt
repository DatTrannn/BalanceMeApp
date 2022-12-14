package com.nutrition.balanceme.presentation.features.add

import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.PathEffect.Companion.dashPathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation

@Composable
    fun ImageUpload(path: Uri?, onClick: () -> Unit) {
        val dashColor = colors.primary
        val radius = CornerRadius(10f, 10f)
        val effect = dashPathEffect(floatArrayOf(10f, 10f), 0f)
        val stroke = Stroke(width = 2f, pathEffect = effect)

        val transformation = RoundedCornersTransformation()
        val painter =
            rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current).data(data = path)
                .apply(block = fun ImageRequest.Builder.() {
                    transformations(transformation)
                }).build())

        Box(
            modifier = Modifier.fillMaxWidth().height(150.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize().clickable { onClick() }) {
                drawRoundRect(style = stroke, color = dashColor, cornerRadius = radius)
            }

            if (path != null)
                Image(
                    painter = painter,
                    contentDescription = "uploaded image",
                    modifier = Modifier.fillMaxSize()
                )
            Text(
                textAlign = TextAlign.Center,
                text = "Tap to upload the photo",
                style = typography.body1
            )

        }
    }