# Distributed E-Commerce AI Agent with Model Context Protocol (MCP) & Spring AI

An enterprise-grade, distributed AI agent architecture built with **Spring Boot 4.x**, **Spring AI 2.0.0**, and Anthropic's **Model Context Protocol (MCP)**.

This project demonstrates how to decouple backend database operations from Large Language Models (LLMs), allowing an AI client to dynamically discover and execute remote tools over a Server-Sent Events (SSE) network stream.

---

##  Architecture Overview

The system is split into two independent Spring Boot applications communicating via MCP:

```text
┌─────────────┐       User Prompt        ┌─────────────┐       MCP Tool Call       ┌──────────────┐
│   Client    │ ──────────────────────>  │    App 2    │ ───────────────────────>  │    App 1     │
│  (Browser)  │                          │ (AI Client) │                           │ (MCP Server) │
└─────────────┘                          └─────────────┘                           └──────────────┘
                                                │                                         │
                                                │ LLM Inference                           │ JPA / Hibernate
                                                ▼                                         ▼
                                         [ Groq / Gemini ]                           [ MySQL DB ]
```

###  Components

* **App 1 (ecom-mcp-server):**
  MCP Server that exposes database operations (products, orders, user inventory) as tools via SSE (`/sse`).

* **App 2 (ecom-mcp-client):**
  MCP Client that:

  * Connects to App 1 on startup
  * Dynamically discovers tool schemas
  * Configures an LLM (Groq / Gemini)
  * Executes automated tool-calling loops

---

##  Tech Stack

* **Backend Framework:** Spring Boot 4.1.0, Spring WebMVC
* **AI Orchestration:** Spring AI 2.0.0 (ChatClient, ToolCallbackProvider)
* **Protocol:** Model Context Protocol (MCP) over Server-Sent Events (SSE)
* **LLM Providers:** Groq API (OpenAI-compatible), Google GenAI (Gemini)
* **Persistence:** Spring Data JPA, Hibernate, MySQL

---

##  Project Structure

```text
├── ecom-mcp-server (App 1)
│   └── src/main/java/in/pavan/ecom_mcp/
│       ├── model/       # JPA Entities (Product, Order, etc.)
│       ├── repository/  # Spring Data Repositories
│       └── tools/       # @Tool annotated service methods exposed to MCP
│
└── ecom-mcp-client (App 2)
    └── src/main/java/in/pavan/ecom_mcp_client/
        └── controller/  # ChatController (Handles REST endpoints & AI client loops)
```

---

##  Getting Started & Configuration

###  Run App 1 (MCP Server)

Configure your MySQL database in:

```
ecom-mcp-server/src/main/resources/application.properties
```

```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/your_ecommerce_db
spring.datasource.username=root
spring.datasource.password=your_password
```

Start the application:

```bash
mvn spring-boot:run
```

Server will run at:

```
http://localhost:8080
```

---

### 2️⃣ Run App 2 (MCP Client)

Configure LLM + MCP connection in:

```
ecom-mcp-client/src/main/resources/application.properties
```

```properties
server.port=8081

# LLM Configuration (Groq Example)
spring.ai.openai.api-key=your_groq_api_key_here
spring.ai.openai.base-url=https://api.groq.com/openai/v1
spring.ai.openai.chat.options.model=llama-3.3-70b-versatile

# MCP Server Connection
spring.ai.mcp.client.sse.connections.ecom-server.url=http://localhost:8080
```

Start the client:

```bash
mvn spring-boot:run
```

Client runs at:

```
http://localhost:8081
```

---

##  API Endpoints

###  App 2 (Client Application)

####  Check Discovered Tools (No API Cost)

```http
GET http://localhost:8081/tools
```

Returns all tool schemas dynamically fetched from MCP Server.

---

####  Execute AI Agent Chat

```http
GET http://localhost:8081/chat?message=What products do you have?
GET http://localhost:8081/chat?message=Get order details with order id 1
```

* Invokes the LLM
* Executes backend tool calls via MCP
* Returns final AI-generated response

---

##  Key Highlights

* Fully **decoupled AI + backend architecture**
* Dynamic **tool discovery via MCP**
* Supports multiple LLM providers (**Groq, Gemini**)
* Efficient **SSE-based streaming communication**
* Clean separation of concerns (**AI vs Data Layer**)

---

##  Future Enhancements

* Add **authentication & authorization layer**
* Integrate **Kafka for async tool execution**
* Introduce **multi-agent orchestration**
* Extend toolset for **analytics & recommendations**

---

##  Summary

This project showcases a **modern AI-native backend architecture** where:

* LLMs don’t directly access databases
* Backend logic is exposed as **structured tools**
* AI agents dynamically decide **what to execute and when**

A strong foundation for building **scalable, production-ready AI systems** 🚀
