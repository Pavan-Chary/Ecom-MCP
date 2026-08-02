package in.pavan.ecom_mcp.repository;

import in.pavan.ecom_mcp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    public boolean existsByEmail(String email);
}
