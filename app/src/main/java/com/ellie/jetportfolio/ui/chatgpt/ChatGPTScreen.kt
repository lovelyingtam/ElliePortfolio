package com.ellie.jetportfolio.ui.chatgpt

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ellie.jetportfolio.R
import com.ellie.jetportfolio.data.LocalDataProvider.profile
import com.ellie.jetportfolio.ui.component.IconTextFloatingActionButton
import com.ellie.jetportfolio.ui.theme.IconLarge
import com.ellie.jetportfolio.ui.theme.JetPortfolioTheme
import com.ellie.jetportfolio.ui.theme.SpaceExtraLarge
import com.ellie.jetportfolio.ui.theme.SpaceLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatGPTScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatGPTViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            IconTextFloatingActionButton(
                onClick = {
                    viewModel.newChat()
                },
                showText = true,
                imageVector = Icons.Default.Add,
                textId = R.string.new_chat,
            )
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            items(items = uiState.messages, key = { it }) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
        InputAreaContent(onClickSend = { message -> viewModel.send(message) })
    }
}

@Composable
private fun MessageListContent() {

}

@Composable
private fun InputAreaContent(
    modifier: Modifier = Modifier,
    onClickSend: (message: String) -> Unit = {}
) {
    Row(modifier.fillMaxWidth()) {
        var text by remember { mutableStateOf("") }
        TextField(modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter some text") }
        )
        IconButton(modifier = Modifier.weight(1F),
            onClick = { onClickSend(text) }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = stringResource(id = R.string.send),
                modifier = Modifier.size(IconLarge),
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScreenPreview() {
    JetPortfolioTheme {
        ChatGPTScreen()
    }
}

