package com.mupper.gpsalarm.android.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mupper.gpsalarm.android.R

private val DarkColorPalette = darkColors(
    primary = Teal50,
    primaryVariant = Teal200,
    secondary = Amber50,
    secondaryVariant = Amber200,
)

private val LightColorPalette = lightColors(
    primary = Teal200,
    primaryVariant = Teal400,
    secondary = Amber200,
    secondaryVariant = Amber400,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun GPSAlarmTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val defaultFontFamily = FontFamily(
        Font(R.font.roboto_medium),
    )
    val typography = Typography(
        defaultFontFamily = defaultFontFamily,
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        content = content,
    )
}

@Composable
@Preview
fun MyApplicationThemePreview() {
    LazyRow {
        items(listOf(true, false)) {
            GPSAlarmTheme(
                darkTheme = it
            ) {
                val colors = listOf(
                    Triple(
                        MaterialTheme.colors.primary,
                        MaterialTheme.colors.onPrimary,
                        "Primary",
                    ),
                    Triple(
                        MaterialTheme.colors.primaryVariant,
                        MaterialTheme.colors.onPrimary,
                        "Primary Variant",
                    ),
                    Triple(
                        MaterialTheme.colors.primarySurface,
                        MaterialTheme.colors.onSurface,
                        "Primary Surface",
                    ),
                    Triple(
                        MaterialTheme.colors.secondary,
                        MaterialTheme.colors.onSecondary,
                        "Secondary",
                    ),
                    Triple(
                        MaterialTheme.colors.secondaryVariant,
                        MaterialTheme.colors.onSecondary,
                        "Secondary Variant",
                    ),
                    Triple(
                        MaterialTheme.colors.error,
                        MaterialTheme.colors.onError,
                        "Error",
                    ),
                    Triple(
                        MaterialTheme.colors.background,
                        MaterialTheme.colors.onBackground,
                        "Background",
                    ),
                    Triple(
                        MaterialTheme.colors.surface,
                        MaterialTheme.colors.onSurface,
                        "Surface",
                    ),
                )
                LazyColumn {
                    items(colors) { color ->
                        Box(
                            modifier = Modifier
                                .width(128.dp)
                                .height(64.dp)
                                .background(color.first),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(color.third, color = color.second, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}