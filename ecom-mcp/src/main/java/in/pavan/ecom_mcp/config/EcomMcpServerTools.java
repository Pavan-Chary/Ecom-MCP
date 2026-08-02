package in.pavan.ecom_mcp.config;

import in.pavan.ecom_mcp.dto.order_dto.OrderCreateRequest;
import in.pavan.ecom_mcp.dto.order_dto.OrderResponse;
import in.pavan.ecom_mcp.dto.product_dto.ProductResponse;
import in.pavan.ecom_mcp.enums.OrderStatus;
import in.pavan.ecom_mcp.service.OrderService;
import in.pavan.ecom_mcp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EcomMcpServerTools {
    private final ProductService productService;
    private final OrderService orderService;

    @McpTool(name = "get_all_products", description = "Fetch the complete list of available products in the catalog.")
    public List<ProductResponse> getAllProducts() {
        System.out.println("I have been called");
        return productService.getAllProducts();
    }

    @McpTool(name = "get_product_details", description = "Get the details of a specific product.")
    public ProductResponse getProductDetails(
            @McpToolParam(description = "The ID of the product to fetch", required = true) Long productId) {
        System.out.println("I have been called");
        return productService.getProductById(productId);
    }

    @McpTool(name = "create_order", description = "Place a new order for a user.")
    public OrderResponse createOrder(
            @McpToolParam(description = "The UUID of the user", required = true) UUID userId,
            @McpToolParam(description = "The order request containing items and address", required = true) OrderCreateRequest orderRequest) {
        System.out.println("I have been called");
        return orderService.createOrder(userId, orderRequest);
    }

    @McpTool(name = "get_order_status", description = "Check the current status of an order.")
    public OrderStatus getOrderStatus(
            @McpToolParam(description = "The ID of the order", required = true) Long orderId) {
        System.out.println("I have been called");
        return orderService.getOrderStatus(orderId);
    }

    @McpTool(name="get_order_details", description = "Get complete details of an order.")
    public OrderResponse getOrderDetails(
            @McpToolParam(description = "The ID of the order", required = true) Long orderId){
        System.out.println("I have been called");
        return orderService.getOrderById(orderId);
    }

    @McpTool(name = "get_all_orders_of_user", description = "Get all orders associated with a particular user using user Id")
    public List<OrderResponse> getAllOrdersOfUser(
            @McpToolParam(description = "The UUID of the user", required=true) UUID userId){
        System.out.println("I have been called");
        return orderService.getAllOrdersByUserId(userId);
    }


}
