package in.pavan.ecom_mcp.service;

import in.pavan.ecom_mcp.dto.product_dto.ProductRequest;
import in.pavan.ecom_mcp.dto.product_dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProducts();
    ProductResponse getProductById(Long productId);
    ProductResponse updateProduct(Long productId, ProductRequest productRequest);
    ProductResponse deleteProduct(Long productId);

}
