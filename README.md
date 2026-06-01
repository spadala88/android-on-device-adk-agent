# Android On-Device ADK Agent Sample

This project serves as a foundational sample demonstrating how to build and deploy AI Agents using the Android Development Kit (ADK) for on-device execution. It provides a basic, runnable architecture that showcases the core principles of agent-based functionality within a mobile environment.

## 🚀 Project Overview

The goal of this application is to illustrate the process of creating an ADK agent that can perform complex tasks locally on the device, minimizing reliance on cloud services and maximizing user privacy and responsiveness.

The current implementation features a basic architecture separating agent logic into two primary components:
*   **Cloud-Based Agents:** For tasks requiring external services or heavy computation.
*   **On-Device Agents:** For secure, private, and fast local processing using tools available directly on the phone.

## 🛠️ Technical Highlights & Core Concepts

### Android ADK Integration
This project heavily utilizes the Android Development Kit (ADK) concepts to demonstrate how system-level functionality can be exposed to an agent. It encapsulates the interaction model, allowing the agent to interact with the device's capabilities through defined interfaces.

### Agent Toolkit Implementation
The `tools` package demonstrates the concept of an agent toolkit. This layer is responsible for providing the agent with structured, callable functions (e.g., `StockTools.kt`) that abstract complex operations, making the agent's logic clean and declarative.

### Testing and Reliability
To ensure the robustness and reliability of the agent's core logic, we have implemented thorough unit testing. Two dedicated test cases have been added to validate the critical paths of the agent's functionality, ensuring that the fundamental building blocks of the architecture are reliable.

## 🏗️ Project Architecture

The architecture is designed around modularity and separation of concerns:

1.  **`app/src/main/java/com/example/adkagent/`**: Contains the main application logic, UI, and orchestration.
2.  **`agent/`**: Holds the core agent implementations (`CloudScreenerAgent`, `OnDeviceScreenerAgent`), which contain the primary decision-making and flow control logic.
3.  **`tools/`**: Houses the utility/tool classes (`StockTools.kt`), representing the external functions the agent can call.
4.  **`viewmodel/`**: Manages the state and business logic for the UI components (`StockScreenerViewModel.kt`).

## ⚙️ Getting Started

1.  **Prerequisites:** Ensure you have the latest Android Studio and the necessary ADK dependencies configured in the build scripts.
2.  **Build:** Run the application module to compile and deploy the sample agent.
3.  **Execution:** The application demonstrates the agent's interaction flow, highlighting the distinction between cloud and local execution paths.

---
*This project is a proof-of-concept and sample model for creating advanced, on-device ADK agents on a phone.*