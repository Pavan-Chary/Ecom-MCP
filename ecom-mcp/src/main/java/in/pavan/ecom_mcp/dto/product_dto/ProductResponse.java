package in.pavan.ecom_mcp.dto.product_dto;

import in.pavan.ecom_mcp.enums.ProductCategory;
import in.pavan.ecom_mcp.model.Product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        ProductCategory category,
        Integer stockQuantity,
        BigDecimal price,
        String description
) {
    public static ProductResponse fromEntity(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getStockQuantity(),
                product.getPrice(),
                product.getDescription()
        );
    }
}
