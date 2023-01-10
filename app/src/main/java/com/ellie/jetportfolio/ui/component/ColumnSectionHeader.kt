package com.ellie.jetportfolio.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ellie.jetportfolio.ui.theme.SpaceMedium

@Composable
fun ColumnSectionHeader(
    modifier: Modifier = Modifier,
    title: String,
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(
                top = SpaceMedium,
                start = SpaceMedium,
                end = SpaceMedium,
            )
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(
                    vertical = SpaceMedium,
                    horizontal = SpaceMedium,
                )
                .fillMaxWidth(),
        )
    }
}