package com.recipes.app_compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.recipes.app_compose.RecipesNavGraph
import com.recipes.app_compose.ui.effect.StatusBarThemeEffect

@Composable
fun RecipesApp() {
    Column(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
    ) {
        StatusBarThemeEffect()

        RecipesNavGraph()
    }
}