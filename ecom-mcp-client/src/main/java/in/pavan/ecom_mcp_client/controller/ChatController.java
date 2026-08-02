package in.pavan.ecom_mcp_client.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ChatController {

    private final ChatClient chatClient;
    private final ToolCallbackProvider mcpTools;


    public ChatController(ChatClient.Builder builder, ToolCallbackProvider mcpTools) {

        this.mcpTools = mcpTools;

        this.chatClient = builder
                .defaultSystem("""
                        You are a highly capable customer support agent for our e-commerce store. 
                        You have access to a suite of external tools to check product inventory, 
                        find product IDs, and place orders. 
                        
                        RULES:
                        - If a user asks about products, ALWAYS search the catalog first using your available tools.
                        - Never say "I don't know" or "I am an AI" without executing your tools.
                        - When placing an order, ensure you find the product ID first before calling the order creation tool.
                        """)
                .defaultTools(mcpTools)
                .build();
    }

    @GetMapping("/tools")
    public List<String> getDiscoveredTools() {

        return Arrays.stream(this.mcpTools.getToolCallbacks())
                .map(callback -> callback.getToolDefinition().name() + " : " + callback.getToolDefinition().description())
                .collect(Collectors.toList());
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(defaultValue = "What products do you have?") String message) {

        return this.chatClient.prompt(message)
                .call()
                .content();
    }
}
