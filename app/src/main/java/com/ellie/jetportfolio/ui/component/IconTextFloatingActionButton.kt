package com.ellie.jetportfolio.ui.component

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ellie.jetportfolio.ui.theme.SpaceSmall

@Composable
fun IconTextFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    showText: Boolean = false,
    imageVector: ImageVector,
    @StringRes textId: Int,
) {
    ExtendedFloatingActionButton(
        modifier = modifier.height(64.dp),
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(id = textId),
        )
        AnimatedVisibility(visible = showText) {
            Text(
                text = stringResource(id = textId),
                modifier = Modifier.padding(start = SpaceSmall),
            )
        }
    }
}