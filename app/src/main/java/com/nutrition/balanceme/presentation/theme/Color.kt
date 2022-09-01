package com.nutrition.balanceme.presentation.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val red400 = Color(0xffCB4335)
val red500 = Color(0xffB03A2E)
val grey200 = Color(0xff566573)
val grey500 = Color(0xff2C394B)
val grey800 = Color(0xff1B1B1E)
val black600 = Color(0xff082032)
val white200 = Color(0xffFBFFFE)
val white300 = Color(0xffEEF0F2)
val Gainsboro = Color(0xFFDADADA)
val RichBlack_Light = Color(0xFF1F3D39)
val RichBlack_Dark = Color(0xFF1C3A36)

val lightColors = lightColors(
    error = red500,
    primary = RichBlack_Light,
    secondary = Gainsboro,
    onSurface = grey800,
    onPrimary = grey800,
    background = white200,
    onSecondary = black600,
    onBackground = black600,
    primaryVariant = RichBlack_Dark,
)

val darkColors = darkColors(
    error = red500,
    primary = white300,
    secondary = grey500,
    onSurface = white200,
    background = black600,
    onSecondary = white200,
    onBackground = white200,
    primaryVariant = grey200,
)