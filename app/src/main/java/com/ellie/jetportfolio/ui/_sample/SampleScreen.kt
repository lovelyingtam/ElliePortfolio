package com.ellie.jetportfolio.ui._sample

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HelpCenter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ellie.jetportfolio.R
import com.ellie.jetportfolio.ui.component.IconTextFloatingActionButton
import com.ellie.jetportfolio.ui.theme.JetPortfolioTheme
import com.ellie.jetportfolio.ui.theme.SpaceExtraLarge
import com.ellie.jetportfolio.ui.theme.SpaceLarge
import com.ellie.jetportfolio.ui.theme.SpaceMedium

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
        Text(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .wrapContentHeight(align = Alignment.CenterVertically),
            text = "+Clicked\n${uiState.count}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge,
        )
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

