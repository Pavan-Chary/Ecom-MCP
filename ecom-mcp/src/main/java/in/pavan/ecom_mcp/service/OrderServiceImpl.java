package in.pavan.ecom_mcp.service;

import in.pavan.ecom_mcp.dto.order_dto.OrderCreateRequest;
import in.pavan.ecom_mcp.dto.order_dto.OrderItemRequest;
import in.pavan.ecom_mcp.dto.order_dto.OrderResponse;
import in.pavan.ecom_mcp.dto.order_dto.OrderUpdateRequest;
import in.pavan.ecom_mcp.enums.OrderStatus;
import in.pavan.ecom_mcp.exceptions.InsufficientStockException;
import in.pavan.ecom_mcp.exceptions.ProductNotExistsException;
import in.pavan.ecom_mcp.exceptions.UserNotExistsException;
import in.pavan.ecom_mcp.model.Order;
import in.pavan.ecom_mcp.model.OrderItem;
import in.pavan.ecom_mcp.model.Product;
import in.pavan.ecom_mcp.model.User;
import in.pavan.ecom_mcp.repository.OrderRepo;
import in.pavan.ecom_mcp.repository.ProductRepo;
import in.pavan.ecom_mcp.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    public OrderServiceImpl(OrderRepo orderRepo, ProductRepo productRepo, UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(UUID userId, OrderCreateRequest orderCreateRequest) {
        Order order = new Order();
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotExistsException("No user exists with this User ID"));

        order.setUser(user);
        order.setStatus(OrderStatus.PROCESSING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(orderCreateRequest.shippingAddress());

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for(OrderItemRequest itemRequest : orderCreateRequest.items()){
            Product product = productRepo.findById(itemRequest.productId())
                    .orElseThrow(() -> new ProductNotExistsException("No product exists with ID: " + itemRequest.productId()));

            if(product.getStockQuantity() < itemRequest.quantity()){
                throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.quantity());
            orderItem.setPriceAtPurchase(product.getPrice());

            totalPrice = totalPrice.add(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())));
            orderItems.add(orderItem);

            // Deducting stock
            product.setStockQuantity(product.getStockQuantity() - itemRequest.quantity());
        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);

        order = orderRepo.save(order);

        return OrderResponse.fromEntity(order);
    }

    @Override
    public List<OrderResponse> getAllOrdersByUserId(UUID userId) {
        // You will need to add: List<Order> findByUserId(UUID userId); to your OrderRepo interface
        return orderRepo.findByUser_Id(userId).stream()
                .map(OrderResponse::fromEntity)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return OrderResponse.fromEntity(order);
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (request.shippingAddress() != null && !request.shippingAddress().isBlank()) {
            order.setShippingAddress(request.shippingAddress());
        }
        if (request.status() != null) {
            order.setStatus(request.status());
        }

        order = orderRepo.save(order);
        return OrderResponse.fromEntity(order);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new RuntimeException("Order is already cancelled");
        }
        if (order.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel a delivered order");
        }

        // Business Logic: Return the items to inventory
        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
        }

        order.setStatus(OrderStatus.CANCELLED);
        order = orderRepo.save(order);
        return OrderResponse.fromEntity(order);
    }

    @Override
    public OrderStatus getOrderStatus(Long orderId) {
        return orderRepo.findById(orderId)
                .map(Order::getStatus)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}