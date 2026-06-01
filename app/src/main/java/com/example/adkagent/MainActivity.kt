package com.example.adkagent

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.adkagent.ui.theme.AdkAgentTheme
import com.example.adkagent.viewmodel.StockScreenerViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: StockScreenerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AdkAgentTheme {
                StockScreenerScreen(viewModel = viewModel)
            }
        }
    }
}
