package in.pavan.ecom_mcp.service;

import in.pavan.ecom_mcp.dto.product_dto.ProductRequest;
import in.pavan.ecom_mcp.dto.product_dto.ProductResponse;
import in.pavan.ecom_mcp.exceptions.ProductAlreadyExistsException;
import in.pavan.ecom_mcp.exceptions.ProductNotExistsException;
import in.pavan.ecom_mcp.model.Product;
import in.pavan.ecom_mcp.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepo productRepo;

    public ProductServiceImpl(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = new Product();

        if(productRepo.existsByName(productRequest.name())){
            throw new ProductAlreadyExistsException("Product already exists with this name");
        }

        product.setName(productRequest.name());
        product.setCategory(productRequest.category());
        product.setPrice(productRequest.price());
        product.setStockQuantity(productRequest.stockQuantity());
        product.setDescription(productRequest.description());
        product = productRepo.save(product);
        return ProductResponse.fromEntity(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepo.findAll().stream().map(ProductResponse::fromEntity).toList();
    }

    @Override
    public ProductResponse getProductById(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(()->new ProductNotExistsException("No product exists with this Product Id"));
        return ProductResponse.fromEntity(product);
    }

    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        Product product = productRepo.findById(productId)
                .orElseThrow(()->new ProductNotExistsException("No product exists with this Product Id"));

        product.setName(productRequest.name());
        product.setCategory(productRequest.category());
        product.setPrice(productRequest.price());
        product.setStockQuantity(productRequest.stockQuantity());
        product.setDescription(productRequest.description());

        product = productRepo.save(product);

        return ProductResponse.fromEntity(product);
    }

    @Override
    public ProductResponse deleteProduct(Long productId) {

        Product product = productRepo.findById(productId)
                .orElseThrow(()->new ProductNotExistsException("No product exists with this Product Id"));
        productRepo.deleteById(productId);
        return ProductResponse.fromEntity(product);

    }
}
