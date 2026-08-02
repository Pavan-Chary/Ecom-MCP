package in.pavan.ecom_mcp.model;

import in.pavan.ecom_mcp.enums.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    private Integer stockQuantity;

    private BigDecimal price;

    private String description;
}