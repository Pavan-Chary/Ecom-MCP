package in.pavan.ecom_mcp.controller;

import in.pavan.ecom_mcp.dto.product_dto.ProductRequest;
import in.pavan.ecom_mcp.dto.product_dto.ProductResponse;
import in.pavan.ecom_mcp.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts(){
        List<ProductResponse> productResponses = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productResponses);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> getByProductId(@PathVariable(name = "productId") Long productId){
        ProductResponse productResponse = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable(name="productId") Long productId, @Valid @RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.updateProduct(productId, productRequest);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable(name="productId") Long productId){
        ProductResponse productResponse = productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

}
