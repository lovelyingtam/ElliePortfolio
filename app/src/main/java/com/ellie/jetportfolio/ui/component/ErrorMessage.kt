package com.ellie.jetportfolio.ui.component

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ellie.jetportfolio.R
import com.ellie.jetportfolio.ui.theme.IconLarge
import com.ellie.jetportfolio.ui.theme.JetPortfolioTheme
import com.ellie.jetportfolio.ui.theme.SpaceLarge
import com.ellie.jetportfolio.ui.theme.SpaceSmall

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    errorMessageState: ErrorMessageState,
) {
    ErrorMessage(
        modifier = modifier,
        title = errorMessageState.getTitle(),
        errMsg = errorMessageState.getMessage(),
    )
}

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    @StringRes titleId: Int? = R.string.error,
    @StringRes errMsgId: Int? = null,
    title: String? = null,
    errMsg: String? = null,
) {
    Column(
        modifier = modifier.padding(SpaceLarge),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(SpaceSmall)
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = null,
                modifier = Modifier.size(IconLarge),
            )
            titleId?.let {
                Text(
                    text = stringResource(id = titleId),
                    style = MaterialTheme.typography.displayMedium,
                )
            } ?: title?.let {
                Text(
                    text = title,
                    style = MaterialTheme.typography.displayMedium,
                )
            }
        }

        Spacer(modifier = Modifier.height(SpaceSmall))

        errMsgId?.let {
            Text(
                text = stringResource(id = errMsgId),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        } ?: errMsg?.let {
            Text(
                text = errMsg,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    JetPortfolioTheme {
        ErrorMessage(errMsg = "No content found")
    }
}