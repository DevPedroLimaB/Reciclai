package com.example.reciclai.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreenLight,
    onPrimary = androidx.compose.ui.graphics.Color.Black,
    primaryContainer = PrimaryGreenDark,
    onPrimaryContainer = androidx.compose.ui.graphics.Color.White,
    secondary = SecondaryBrownLight,
    onSecondary = androidx.compose.ui.graphics.Color.Black,
    secondaryContainer = SecondaryBrownDark,
    onSecondaryContainer = androidx.compose.ui.graphics.Color.White,
    tertiary = AccentBlue,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    error = Error,
    onError = androidx.compose.ui.graphics.Color.White,
    background = androidx.compose.ui.graphics.Color(0xFF121212),
    onBackground = androidx.compose.ui.graphics.Color.White,
    surface = androidx.compose.ui.graphics.Color(0xFF1E1E1E),
    onSurface = androidx.compose.ui.graphics.Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = androidx.compose.ui.graphics.Color.White,
    primaryContainer = PrimaryGreenLight,
    onPrimaryContainer = androidx.compose.ui.graphics.Color.Black,
    secondary = SecondaryBrown,
    onSecondary = androidx.compose.ui.graphics.Color.White,
    secondaryContainer = SecondaryBrownLight,
    onSecondaryContainer = androidx.compose.ui.graphics.Color.Black,
    tertiary = AccentBlue,
    onTertiary = androidx.compose.ui.graphics.Color.White,
    error = Error,
    onError = androidx.compose.ui.graphics.Color.White,
    background = androidx.compose.ui.graphics.Color(0xFFFAFAFA),
    onBackground = androidx.compose.ui.graphics.Color.Black,
    surface = androidx.compose.ui.graphics.Color.White,
    onSurface = androidx.compose.ui.graphics.Color.Black,
)

@Composable
fun ReciclaiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}