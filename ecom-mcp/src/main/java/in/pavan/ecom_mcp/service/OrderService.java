package in.pavan.ecom_mcp.service;

import in.pavan.ecom_mcp.dto.order_dto.OrderCreateRequest;
import in.pavan.ecom_mcp.dto.order_dto.OrderResponse;
import in.pavan.ecom_mcp.dto.order_dto.OrderUpdateRequest;
import in.pavan.ecom_mcp.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    OrderResponse createOrder(UUID userId, OrderCreateRequest orderCreateRequest);
    List<OrderResponse> getAllOrdersByUserId(UUID userId);
    OrderResponse getOrderById(Long orderId);
    OrderResponse updateOrder(Long orderId, OrderUpdateRequest orderUpdateRequest);
    OrderResponse cancelOrder(Long orderId);
    OrderStatus getOrderStatus(Long orderId);

}
