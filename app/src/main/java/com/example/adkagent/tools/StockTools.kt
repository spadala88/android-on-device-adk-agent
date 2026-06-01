package com.example.adkagent.tools

import android.util.Log
import com.google.adk.kt.annotations.Param
import com.google.adk.kt.annotations.Tool

object StockTools {

    @Tool(
        name = "tool_send_email_to_user",
        description = "Send stock information to user"
    )
    fun sendEmailToUser(
        @Param(description = "The stock ticker symbol (e.g., AAPL)") ticker: String
    ): String {
        Log.d("tool_call","Sending  stock news email to user")
        return "Email sent successfully"
    }

    @Tool(
        name = "tool_analyze_stock_news",
        description = "Analyze all data and summarize."
    )
    fun analyzeStockNews(
        @Param(description = "Stocks news data") rawNewsData: String
    ): String {
        Log.d("tool_call","Analyze and summarizing stock news")
        return "return summarized data"
    }

    @Tool(
        name = "tool_get_stock_news",
        description = "Fetch recent news headlines."
    )
    fun getStockNews(
        @Param(description = "The stock ticker symbol (e.g., GOOGL)") ticker: String
    ): String {
        Log.d("tool_call","Getting stock news")
        return "Search and return All stocks data from internet."
    }
}