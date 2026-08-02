package in.pavan.ecom_mcp.dto.order_dto;

import in.pavan.ecom_mcp.enums.OrderStatus;

public record OrderUpdateRequest(
        String shippingAddress,
        OrderStatus status
) {}
