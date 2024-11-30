package com.recipes.app_compose.ui.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recipes.app_compose.ui.component.HorizontalDottedProgress.BounceDotRadius
import com.recipes.app_compose.ui.component.HorizontalDottedProgress.DotAmount
import com.recipes.app_compose.ui.component.HorizontalDottedProgress.DotRadius
import com.recipes.app_compose.ui.component.HorizontalDottedProgress.Offset
import com.recipes.app_compose.ui.component.HorizontalDottedProgress.Padding

@Preview
@Composable
fun HorizontalDottedProgress(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "radius scale")

    // Animate each dot's radius with a staggered delay to create a wave effect
    val radii = List(DotAmount) { index ->
        infiniteTransition.animateFloat(
            initialValue = DotRadius.value,
            targetValue = BounceDotRadius.value,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 300, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
                initialStartOffset = StartOffset(index * 100)
            ),
            label = "radius scale"
        )
    }

    Canvas(modifier = modifier.size(width = Offset * DotAmount, height = BounceDotRadius * 2)) {
        for (i in 0 until DotAmount) {
            drawCircle(
                color = Color(0xFFFD583F),
                radius = radii[i].value.dp.toPx(),
                center = androidx.compose.ui.geometry.Offset(
                    x = Padding.toPx() + (i * Offset.toPx()),
                    y = BounceDotRadius.toPx()
                )
            )
        }
    }
}

private data object HorizontalDottedProgress {
    val DotRadius = 5.dp
    val BounceDotRadius = 8.dp
    val DotAmount = 10
    val Padding = 10.dp
    val Offset = 20.dp
}