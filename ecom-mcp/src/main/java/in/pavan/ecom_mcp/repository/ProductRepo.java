package in.pavan.ecom_mcp.repository;

import in.pavan.ecom_mcp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
    public boolean existsByName(String name);
}
