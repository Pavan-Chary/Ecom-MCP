package in.pavan.ecom_mcp.repository;

import in.pavan.ecom_mcp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}
