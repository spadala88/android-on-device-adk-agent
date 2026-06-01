package com.example.adkagent

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.example.adkagent.viewmodel.StockScreenerViewModel
import com.example.adkagent.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreenerScreen(viewModel: StockScreenerViewModel) {
    var tickerInput by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Stock Screener") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = tickerInput,
                    onValueChange = { tickerInput = it.uppercase() },
                    label = { Text("Ticker Symbol") },
                    placeholder = { Text("e.g. MSFT") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = { if (tickerInput.isNotBlank()) viewModel.runStockAnalysis(tickerInput) },
                    enabled = uiState !is UiState.Loading
                ) {
                    Text("Analyze")
                }
            }

            AnimatedVisibility(visible = uiState is UiState.Loading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Gemini Cloud analyzing metrics securely...", style = MaterialTheme.typography.bodySmall)
                }
            }

            if (uiState is UiState.Error) {
                Text(text = (uiState as UiState.Error).message, color = MaterialTheme.colorScheme.error)
            }

            if (uiState is UiState.Success) {
                Card(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = (uiState as UiState.Success).reportText,
                            style = MaterialTheme.typography.bodyMedium.copy(fontFamily = FontFamily.Monospace)
                        )
                    }
                }
            }
        }
    }
}