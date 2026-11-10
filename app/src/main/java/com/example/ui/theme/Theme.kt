package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = FleetPrimary,
    onPrimary = Color.White,
    primaryContainer = FleetPrimaryContainer,
    onPrimaryContainer = FleetOnPrimaryContainer,
    secondary = FleetSecondary,
    onSecondary = Color.White,
    secondaryContainer = FleetSecondaryContainer,
    onSecondaryContainer = FleetOnSecondaryContainer,
    tertiary = FleetStatusWarning,
    onTertiary = Color.White,
    tertiaryContainer = FleetStatusWarningBg,
    onTertiaryContainer = Color(0xFF78350F),
    background = FleetLightBlueBackground,
    onBackground = FleetDarkText,
    surface = FleetSurface,
    onSurface = FleetDarkText,
    surfaceVariant = FleetSurfaceVariant,
    onSurfaceVariant = FleetMutedText,
    outline = FleetBorder,
    error = FleetStatusDanger,
    onError = Color.White,
    errorContainer = FleetStatusDangerBg,
    onErrorContainer = Color(0xFF7F1D1D)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF38BDF8),
    onPrimary = Color(0xFF082F49),
    primaryContainer = Color(0xFF0369A1),
    onPrimaryContainer = Color(0xFFE0F2FE),
    secondary = Color(0xFF7DD3FC),
    onSecondary = Color(0xFF082F49),
    secondaryContainer = Color(0xFF075985),
    onSecondaryContainer = Color(0xFFE0F2FE),
    background = Color(0xFF0F172A),
    onBackground = Color(0xFFF8FAFC),
    surface = Color(0xFF1E293B),
    onSurface = Color(0xFFF8FAFC),
    surfaceVariant = Color(0xFF334155),
    onSurfaceVariant = Color(0xFFCBD5E1),
    outline = Color(0xFF475569)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep intentional light blue branding
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
