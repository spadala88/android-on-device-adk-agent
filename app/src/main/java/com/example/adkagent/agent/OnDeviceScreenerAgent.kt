package com.example.adkagent.agent

import com.example.adkagent.tools.StockTools
import com.example.adkagent.tools.generatedTools
import com.google.adk.kt.agents.Instruction
import com.google.adk.kt.agents.LlmAgent
import com.google.adk.kt.models.mlkit.GenaiPrompt
import com.google.mlkit.genai.prompt.GenerativeModel

class OnDeviceScreenerAgent(generativeModel: GenerativeModel) {

    val agent = LlmAgent(
        name = "screener_agent",
        description = "On-device expert stock analysis agent.",
        model = GenaiPrompt.create(
            generativeModel,
            "gemini-nano",
        ),
        instruction = Instruction(
            """
            You are an expert stock analysis agent. When given a stock ticker:
            1. ALWAYS call all three tools: tool_get_stock_news, tool_get_stock_financials, tool_get_stock_price_history.
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