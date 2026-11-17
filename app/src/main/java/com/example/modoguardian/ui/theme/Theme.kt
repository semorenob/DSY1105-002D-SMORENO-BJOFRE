package com.example.modoguardian.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Colores semanticos que no forman parte del ColorScheme de Material 3.
 * Se exponen via [GuardianTheme.colors] para mantener una sola fuente de verdad.
 */
@Immutable
data class GuardianColors(
    val success: Color,
    val successContainer: Color,
    val warning: Color,
    val warningContainer: Color,
    val critical: Color,
    val criticalContainer: Color,
    val border: Color,
    val glowCritical: Color,
    val glowPrimary: Color,
    val glowSuccess: Color,
    val isDark: Boolean
)

private val DarkGuardianColors = GuardianColors(
    success = SuccessGreen,
    successContainer = SuccessContainerDark,
    warning = WarningAmber,
    warningContainer = WarningContainerDark,
    critical = CriticalRed,
    criticalContainer = CriticalContainerDark,
    border = BorderDark,
    glowCritical = GlowCriticalDark,
    glowPrimary = GlowPrimaryDark,
    glowSuccess = GlowSuccessDark,
    isDark = true
)

private val LightGuardianColors = GuardianColors(
    success = Color(0xFF059669),
    successContainer = SuccessContainerLight,
    warning = Color(0xFFB45309),
    warningContainer = WarningContainerLight,
    critical = Color(0xFFDC2626),
    criticalContainer = CriticalContainerLight,
    border = BorderLight,
    glowCritical = GlowCriticalLight,
    glowPrimary = GlowPrimaryLight,
    glowSuccess = GlowSuccessLight,
    isDark = false
)

private val LocalGuardianColors = staticCompositionLocalOf { DarkGuardianColors }

object GuardianTheme {
    val colors: GuardianColors
        @Composable
        @ReadOnlyComposable
        get() = LocalGuardianColors.current
}

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    onPrimary = Color.White,
    primaryContainer = SurfaceVariantDark,
    onPrimaryContainer = Color.White,
    secondary = SuccessGreen,
    onSecondary = Color.White,
    tertiary = WarningAmber,
    onTertiary = Color(0xFF1F1A0B),
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    surfaceContainer = SurfaceDark,
    surfaceContainerHigh = SurfaceVariantDark,
    surfaceContainerHighest = SurfaceVariantDark,
    outline = BorderDark,
    outlineVariant = BorderDark,
    error = CriticalRed,
    onError = Color.White,
    errorContainer = CriticalContainerDark,
    onErrorContainer = Color(0xFFFFDAD6),
    scrim = Color(0xCC000000)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryBlueDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFDBEAFE),
    onPrimaryContainer = Color(0xFF1E3A8A),
    secondary = Color(0xFF059669),
    onSecondary = Color.White,
    tertiary = Color(0xFFB45309),
    onTertiary = Color.White,
    background = BackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,
    surfaceContainer = SurfaceLight,
    surfaceContainerHigh = SurfaceVariantLight,
    surfaceContainerHighest = SurfaceVariantLight,
    outline = BorderLight,
    outlineVariant = BorderLight,
    error = Color(0xFFDC2626),
    onError = Color.White,
    errorContainer = CriticalContainerLight,
    onErrorContainer = Color(0xFF7F1D1D),
    scrim = Color(0x66000000)
)

@Composable
fun ModoGuardianTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkGuardianColors else LightGuardianColors

    CompositionLocalProvider(LocalGuardianColors provides colors) {
        MaterialTheme(
            colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
            typography = Typography,
            content = content
        )
    }
}
