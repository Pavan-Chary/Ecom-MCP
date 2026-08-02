package in.pavan.ecom_mcp.controller;

import in.pavan.ecom_mcp.dto.order_dto.OrderCreateRequest;
import in.pavan.ecom_mcp.dto.order_dto.OrderResponse;
import in.pavan.ecom_mcp.dto.order_dto.OrderUpdateRequest;
import in.pavan.ecom_mcp.enums.OrderStatus;
import in.pavan.ecom_mcp.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<OrderResponse> createOrder(
            @PathVariable(name = "userId") UUID userId,
            @Valid @RequestBody OrderCreateRequest orderRequest) {
        OrderResponse orderResponse = orderService.createOrder(userId, orderRequest);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<List<OrderResponse>> getUserOrders(
            @PathVariable(name = "userId") UUID userId) {
        List<OrderResponse> allOrderOfUser = orderService.getAllOrdersByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(allOrderOfUser);
    }


    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable(name = "orderId") Long orderId) {
        OrderResponse orderResponse = orderService.getOrderById(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(
            @PathVariable(name = "orderId") Long orderId,
            @Valid @RequestBody OrderUpdateRequest updateRequest) {
        OrderResponse orderResponse = orderService.updateOrder(orderId, updateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

    @PutMapping("/orders/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable(name = "orderId") Long orderId) {
        OrderResponse orderResponse = orderService.cancelOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

    @GetMapping("/orders/{orderId}/status")
    public ResponseEntity<OrderStatus> getOrderStatus(
            @PathVariable(name = "orderId") Long orderId) {
        OrderStatus orderStatus = orderService.getOrderStatus(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderStatus);
    }


}
