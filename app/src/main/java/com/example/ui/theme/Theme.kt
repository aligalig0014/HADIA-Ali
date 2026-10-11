package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val RomanticDarkColorScheme = darkColorScheme(
  primary = DarkPrimary,
  secondary = DarkSecondary,
  tertiary = DarkTertiary,
  background = DarkBackground,
  surface = DarkSurface,
  onPrimary = Color.White,
  onSecondary = Color(0xFF260515),
  onTertiary = Color.White,
  onBackground = Color(0xFFFFF0F5),
  onSurface = Color(0xFFFFF0F5)
)

private val RomanticLightColorScheme = lightColorScheme(
  primary = LightPrimary,
  secondary = LightSecondary,
  tertiary = LightTertiary,
  background = LightBackground,
  surface = LightSurface,
  onPrimary = Color.White,
  onSecondary = Color(0xFF14020C),
  onTertiary = Color.White,
  onBackground = Color(0xFF1E0312),
  onSurface = Color(0xFF1E0312)
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Default to romantic starry dark theme
  dynamicColor: Boolean = false, // Keep curated romantic velvet palette
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) RomanticDarkColorScheme else RomanticLightColorScheme
  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
