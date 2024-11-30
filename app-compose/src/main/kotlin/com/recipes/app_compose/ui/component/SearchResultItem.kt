package com.recipes.app_compose.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.recipes.app_compose.ui.component.SearchItemDefaults.ImageCornerRadius
import com.recipes.app_compose.ui.component.SearchItemDefaults.ImageSize
import com.recipes.app_compose.ui.component.SearchItemDefaults.InnerSpacing

@Composable
fun SearchResultItem(
    state: SearchResultItemUiState,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        Image(state.imageUrl)
        Spacer(modifier = Modifier.width(InnerSpacing))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(1f)
        ) {
            Title(state.title)
            Subtitle(state.subtitle)
        }
        Arrow()
    }
}

@Composable
private fun Image(imageUrl: String) {
    Image(
        imageUrl = imageUrl,
        contentDescription = null,
        modifier = Modifier
            .size(ImageSize)
            .clip(RoundedCornerShape(ImageCornerRadius))
    )
}

@Composable
private fun Title(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        maxLines = 2,
        modifier = Modifier.padding(bottom = 0.dp)
    )
}

@Composable
private fun Subtitle(subtitle: String) {
    Text(
        text = subtitle,
        style = MaterialTheme.typography.titleSmall,
        maxLines = 1,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = .7f),
        modifier = Modifier.padding(bottom = 0.dp)
    )
}

@Composable
private fun Arrow() {
    Icon(
        painter = rememberVectorPainter(Icons.AutoMirrored.Filled.KeyboardArrowRight),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = .7f),
        modifier = Modifier.padding(start = InnerSpacing)

    )
}

@Preview
@Composable
private fun SearchResultItemPreview() {
    Surface {
        SearchResultItem(
            state = SearchResultItemUiState(
                title = "Pizza",
                subtitle = "Food Channel",
                imageUrl = "",
                id = ""
            ),
            onClick = {}
        )
    }
}

data class SearchResultItemUiState(
    val title: String,
    val subtitle: String,
    val imageUrl: String,
    val id: String,
)

private object SearchItemDefaults {
    val ImageSize = 64.dp
    val ImageCornerRadius = 12.dp
    val InnerSpacing = 12.dp
}
