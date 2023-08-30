package com.rmoralf.xkcd.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val _lightColorPalette = lightColors(
//    primary = Purple500,
//    primaryVariant = Purple700,
//    secondary = Teal200,
    primary = xkcdPrimary,
    primaryVariant = xkcdPrimaryVariant,
    secondary = xkcdSecondary,

    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun XkcdTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        colors = _lightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}