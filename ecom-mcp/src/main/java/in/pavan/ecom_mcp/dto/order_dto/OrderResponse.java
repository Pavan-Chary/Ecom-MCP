package in.pavan.ecom_mcp.dto.order_dto;

import in.pavan.ecom_mcp.enums.OrderStatus;
import in.pavan.ecom_mcp.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        Long id,
        UUID userId,
        OrderStatus status,
        LocalDateTime orderDate,
        String shippingAddress,
        BigDecimal totalPrice,
        List<OrderItemResponse> items
) {
    public static OrderResponse fromEntity(Order order) {
        List<OrderItemResponse> itemResponses = order.getOrderItems().stream()
                .map(OrderItemResponse::fromEntity)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getStatus(),
                order.getOrderDate(),
                order.getShippingAddress(),
                order.getTotalPrice(),
                itemResponses
        );
    }
}
