package com.ellie.jetportfolio.ui.component

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import com.ellie.jetportfolio.R
import com.ellie.jetportfolio.ui.theme.IconSmall
import com.ellie.jetportfolio.ui.theme.SpaceSmall
import timber.log.Timber

@Composable
fun ClipboardIconLabel(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    icon: ImageVector,
    label: String,
    text: String,
) {
    val context: Context = LocalContext.current
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Row(
        modifier = modifier.clickable {
            Timber.d("Copied: $text")
            if (onClick == null) {
                clipboardManager.setText(AnnotatedString((text)))
                Toast.makeText(
                    context,
                    context.getString(R.string.copied),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                onClick()
            }
        },
        horizontalArrangement = Arrangement.spacedBy(SpaceSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            modifier = Modifier.size(IconSmall),
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            modifier = Modifier,
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                textDecoration = if (onClick == null) TextDecoration.None else TextDecoration.Underline,
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}