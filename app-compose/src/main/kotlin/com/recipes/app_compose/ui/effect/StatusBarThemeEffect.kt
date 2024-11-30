package com.recipes.app_compose.ui.effect

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

@Composable
fun StatusBarThemeEffect(isLight: Boolean = !isSystemInDarkTheme()) {
    val activity = LocalContext.current as? Activity

    LaunchedEffect(Unit) {
        activity?.window?.let { window ->
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.isAppearanceLightStatusBars = isLight
        }
    }
}