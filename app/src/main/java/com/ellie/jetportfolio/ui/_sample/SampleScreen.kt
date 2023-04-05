package com.ellie.jetportfolio.ui._sample

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ellie.jetportfolio.R
import com.ellie.jetportfolio.ui.component.IconTextFloatingActionButton
import com.ellie.jetportfolio.ui.theme.JetPortfolioTheme
import com.ellie.jetportfolio.ui.theme.SpaceLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleScreen(
    modifier: Modifier = Modifier,
    viewModel: SampleViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            IconTextFloatingActionButton(
                onClick = {
                    viewModel.add()
                },
                showText = true,
                imageVector = Icons.Default.Add,
                textId = R.string.add,
            )
        },
    ) {
        Column {
            if (uiState.isRefreshing) {
                // Loading bar
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            Text(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(),
                text = "+Clicked: ${uiState.count}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
            )
            Text(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(),
                text = "${uiState.uuidModel}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
            )
            Button(
                modifier = Modifier
                    .padding(SpaceLarge)
                    .fillMaxWidth(),
                onClick = { viewModel.fetchUUID() },
            ) {
                Text(text = "Simple Button")
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScreenPreview() {
    JetPortfolioTheme {
        SampleScreen()
    }
}

