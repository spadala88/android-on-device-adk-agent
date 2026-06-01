# Android On-Device ADK Agent Sample

This project serves as a comprehensive, runnable sample demonstrating the end-to-end lifecycle of building and deploying AI Agents using the Android Development Kit (ADK) for on-device execution. It showcases a sophisticated, modular architecture that manages task execution between cloud services and local device capabilities.

## 🚀 Project Overview: The Agent Lifecycle

This application simulates the full process of a modern AI agent interacting with a phone. The goal is to provide a seamless, high-performance experience while ensuring data privacy and optimal user experience.

The agent lifecycle is as follows:
1.  **User Input:** The user interacts with the app by entering a prompt or command.
2.  **Cloud LLM Mediation:** The input is first routed to a Cloud Large Language Model (LLM). The LLM interprets the request and determines the best action.
3.  **On-Device Agent Selection:** Based on the LLM's structured response, the core `OnDeviceScreenerAgent` logic selects the appropriate local tool or module to handle the task, ensuring low latency and local data processing.
4.  **Tool Execution:** The chosen tool (e.g., `StockTools.kt`) executes the function, providing the final result back to the user interface.

This architecture minimizes reliance on constant cloud connectivity while maximizing local intelligence.

## 🛠️ Technical Highlights & Core Concepts

### Android ADK Integration
We utilize ADK concepts to define structured interfaces, allowing the agent to interact with device-level functionalities through defined, abstract tools. This ensures the agent remains context-aware of the device's capabilities.

### Agent Toolkit Implementation
The `tools` package represents the agent's toolkit. It contains structured, callable functions (like `StockTools.kt`) that encapsulate complex, repeatable operations. This abstraction layer is crucial for keeping the main agent logic clean and declarative.

### Observability & Debugging (Tool Logging)
For advanced debugging, we have added comprehensive logging to the three core tool execution paths. **To trace the agent's decision-making process and view tool call arguments, please check the device logs (Logcat)** while running the app. These logs will show exactly which tool was called and with what parameters, allowing for deep architectural inspection.

### Testing and Reliability (Unit Testing)
To ensure the robustness and reliability of the agent's core logic, we implement comprehensive unit testing across multiple layers:
*   **Unit Tests:** Validate the smallest, isolated components (e.g., individual functions within `StockTools.kt`), ensuring they perform correctly under specific conditions.
*   **Integration Tests:** Validate the interaction between major components (e.g., how the `OnDeviceScreenerAgent` successfully calls and interprets results from `StockTools.kt`), confirming the entire workflow path is sound.

### Key Configuration
All necessary credentials and API keys for cloud services are managed within the dedicated **`buildconfig`** file (or similar configuration structure) to ensure sensitive information is isolated and managed separately from the core application logic.

## 🏗️ Project Architecture

The system is highly modular, promoting maintainability and scalability:

1.  **`app/src/main/java/com/example/adkagent/`**: Contains the main application logic, UI, and overall orchestration.
2.  **`agent/`**: Houses the core agent implementations (`CloudScreenerAgent`, `OnDeviceScreenerAgent`), which manage the complex flow from LLM response to tool invocation.
3.  **`tools/`**: Houses the utility/tool classes (`StockTools.kt`), representing the external, callable functions that expand the agent's capabilities.
4.  **`viewmodel/`**: Manages the state and business logic for the UI components.

## ⚙️ Getting Started

1.  **Prerequisites:** Ensure you have the latest Android Studio and necessary ADK dependencies configured.
2.  **Initial Setup:** Verify that all API keys and build configuration details are correctly placed in the specified `buildconfig` area.
3.  **Execution Flow:**
    *   Run the application module.
    *   Type a prompt into the input field. Observe how the agent processes the request, first communicating with the cloud, and then executing the necessary local tool based on the LLM's interpretation.
4.  **Debugging:** Always check **Logcat** during testing to monitor the tool calls and agent flow.

---
*This project is a proof-of-concept and sample model for creating advanced, on-device ADK agents on a phone.*