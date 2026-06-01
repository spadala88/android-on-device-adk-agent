package com.example.adkagent.agent

import com.example.adkagent.BuildConfig
import com.example.adkagent.tools.StockTools
import com.example.adkagent.tools.generatedTools
import com.google.adk.kt.agents.Instruction
import com.google.adk.kt.agents.LlmAgent
import com.google.adk.kt.models.Gemini
import com.google.adk.kt.models.Model

class CloudScreenerAgent(modelOverride: Model? = null) {

    val agent = LlmAgent(
        name = "screener_agent",
        description = "Cloud-powered expert stock analysis agent.",
        model = modelOverride ?: Gemini(
            name = "gemini-flash-latest",
            apiKey = BuildConfig.GEMINI_API_KEY,
        ),
        instruction = Instruction(
            """
            You are an expert stock analysis agent. When given a stock ticker:
            1. ALWAYS call all three tools: getStockNews, analyzeStockNews, sendEmailToUser.
            2. Analyze the data and provide your recommendation in the exact format requested.
            3. You must keep calling tools until you have all the information required for the template.

            ─────────────────────────────────────
            STOCK: [Company Name] ([TICKER])
            ─────────────────────────────────────
            DECISION: BUY / SELL / HOLD
            CONFIDENCE: High / Medium / Low
            ─────────────────────────────────────
            """.trimIndent(),
        ),
        tools = StockTools.generatedTools()
    )
}