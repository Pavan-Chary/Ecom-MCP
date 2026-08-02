package in.pavan.ecom_mcp.dto.product_dto;

import in.pavan.ecom_mcp.enums.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message="Name should not be blank")
        String name,
        @NotNull(message="Category should not be Null")
        ProductCategory category,
        @NotNull(message="StockQuantity should not be null")
        @PositiveOrZero(message = "Provide a valid Stock quantity(+ve)")
        Integer stockQuantity,
        @NotNull(message="Price should not be null")
        @Positive(message = "Provide a valid positive number")
        BigDecimal price,
        @NotBlank(message="Description should not be blank")
        String description
) {
}
