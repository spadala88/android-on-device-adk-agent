package com.example.adkagent.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.adkagent.agent.CloudScreenerAgent
import com.google.adk.kt.agents.LlmAgent
import com.google.adk.kt.runners.InMemoryRunner
import com.google.adk.kt.types.Content
import com.google.adk.kt.types.Role
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed interface UiState {
    object Idle : UiState
    object Loading : UiState
    data class Success(val reportText: String) : UiState
    data class Error(val message: String) : UiState
}

class StockScreenerViewModel(application: Application) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState

    private var cloudAgent: LlmAgent? = null

    fun runStockAnalysis(ticker: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                // Ensure agent is initialized.
                if (cloudAgent == null) {
                    Log.d("StockScreener", "Initializing Cloud Agent...")
                    cloudAgent = CloudScreenerAgent().agent
                }

                // Run the analysis on a background thread to avoid NetworkOnMainThreadException.
                withContext(Dispatchers.IO) {
                    val runner = InMemoryRunner(cloudAgent!!)
                    runner.runAsync(
                        userId = "user",
                        sessionId = "session",
                        newMessage = Content.fromText(Role.USER, "Analyze $ticker")
                    ).collect { event ->
                        if (event.isFinalResponse) {
                            val text = event.content?.parts?.joinToString("\n") { it.text ?: "" }
                            if (!text.isNullOrBlank()) {
                                withContext(Dispatchers.Main) {
                                    _uiState.value = UiState.Success(text)
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("StockScreener", "Error during analysis", e)
                _uiState.value = UiState.Error(
                    e.localizedMessage ?: "Analysis failed. Please check your internet connection or API key."
                )
            }
        }
    }
}