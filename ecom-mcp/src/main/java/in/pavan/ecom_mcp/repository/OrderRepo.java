package in.pavan.ecom_mcp.repository;

import in.pavan.ecom_mcp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByUser_Id(UUID userId);
}
