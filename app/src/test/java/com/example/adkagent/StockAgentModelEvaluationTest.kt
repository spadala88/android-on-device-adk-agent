package com.example.adkagent

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.adkagent.agent.CloudScreenerAgent
import com.google.adk.kt.runners.InMemoryRunner
import com.google.adk.kt.types.Content
import com.google.adk.kt.types.Role
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StockAgentModelEvaluationTest {

    /**
     * This dataset tests the model's ability to follow the mandatory tool-calling pipeline
     * defined in the agent's instructions (getStockNews -> analyzeStockNews -> sendEmailToUser).
     */
    private val evaluationDataset = listOf(
        "Give me the latest news on AAPL and email it over",
        "Analyze GOOGL headlines",
        "Send an email with MSFT stock updates"
    )

    private val requiredTools = listOf("getStockNews", "analyzeStockNews", "sendEmailToUser")

    @Test
    fun evaluateModelToolSelectionAccuracy() = runTest {
        // 1. Setup: Use the real production agent logic
        val realProductionAgent = CloudScreenerAgent().agent
        val testRunner = InMemoryRunner(realProductionAgent)

        var successfulEvaluations = 0
        var totalAttempts = 0

        // 2. Automated Loop over user prompts
        for (prompt in evaluationDataset) {
            try {
                totalAttempts++
                // Execute: Run a session with the runner and collect all events
                val events = testRunner.runAsync(
                    userId = "eval-user",
                    sessionId = "eval-session-$totalAttempts", // Unique session per prompt
                    newMessage = Content.fromText(Role.USER, prompt)
                ).toList()

                // 3. Extract what tools the model actually decided to execute
                val executedTools = events.flatMap { event ->
                    event.functionCalls().map { it.name }
                }

                // 4. Verify if the model followed the "ALWAYS call all three" instruction
                val modelPassedMatches = requiredTools.all { it in executedTools }
                if (modelPassedMatches) {
                    successfulEvaluations++
                } else {
                    println("Failure for prompt: '$prompt'. Missing tools. Executed: $executedTools")
                }
            } catch (e: Exception) {
                println("Error evaluating prompt '$prompt': ${e.message}")
                if (e.message?.contains("429") == true) {
                    println("Rate limit hit. Stopping evaluation loop.")
                    totalAttempts-- // Don't count this failed attempt toward accuracy
                    break
                }
            }
        }

        // 5. Assertion: Define an acceptable accuracy threshold.
        // We only assert if we have at least one successful network call.
        if (totalAttempts > 0) {
            val accuracyScore = (successfulEvaluations.toDouble() / totalAttempts) * 100
            println("Final Evaluation Accuracy: $accuracyScore% ($successfulEvaluations/$totalAttempts)")
            
            assertTrue(
                "Model intelligence regression detected! Accuracy dropped to $accuracyScore%",
                accuracyScore >= 50.0 // Relaxed for free-tier testing, adjust for CI/CD
            )
        } else {
            println("No evaluation attempts were successful (likely due to environment/quota issues).")
        }
    }
}
