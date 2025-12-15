package com.example.reciclai.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Paleta Verde Sustentável do Reciclai
val GreenPrimary = Color(0xFF2E7D32)      // Verde escuro principal
val GreenSecondary = Color(0xFF4CAF50)    // Verde médio
val GreenTertiary = Color(0xFF81C784)     // Verde claro
val GreenAccent = Color(0xFF66BB6A)       // Verde accent
val GreenLight = Color(0xFFE8F5E8)        // Verde muito claro (fundos)
val GreenDark = Color(0xFF1B5E20)         // Verde muito escuro

val WhiteGreen = Color(0xFFF1F8E9)        // Branco esverdeado
val GrayGreen = Color(0xFF8BC34A)         // Cinza esverdeado

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    secondary = GreenSecondary,
    onSecondary = Color.White,
    tertiary = GreenTertiary,
    onTertiary = Color.White,
    background = WhiteGreen,
    onBackground = GreenDark,
    surface = Color.White,
    onSurface = GreenDark,
    surfaceVariant = GreenLight,
    onSurfaceVariant = GreenPrimary,
    outline = GreenAccent,
    error = Color(0xFFD32F2F),
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = GreenSecondary,
    onPrimary = GreenDark,
    secondary = GreenTertiary,
    onSecondary = GreenDark,
    tertiary = GreenAccent,
    onTertiary = GreenDark,
    background = Color(0xFF0D1B0F),
    onBackground = GreenLight,
    surface = Color(0xFF1A2C1D),
    onSurface = GreenLight,
    surfaceVariant = GreenDark,
    onSurfaceVariant = GreenTertiary,
    outline = GreenAccent
)

@Composable
fun ReciclaiTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = ReciclaiTypography,
        content = content
    )
}
