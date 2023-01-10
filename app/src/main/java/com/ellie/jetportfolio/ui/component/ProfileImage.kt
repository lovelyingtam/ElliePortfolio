package com.ellie.jetportfolio.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ellie.jetportfolio.R

@Composable
fun ProfileImage(
    modifier: Modifier = Modifier,
    url: String?,
    size: Dp,
    borderWidth: Dp = 2.dp,
) {
    NetworkImage(
        url = url,
        contentDescription = stringResource(id = R.string.profile_picture),
        modifier = modifier
            .clip(CircleShape)
            .background(Color.White)
            .border(
                width = borderWidth,
                color = Color.White,
                shape = CircleShape,
            )
            .size(size),
    )
}