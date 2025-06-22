package com.ecom.service;

import com.ecom.dto.product.ProductRequest;
import com.ecom.dto.product.ProductResponse;
import com.ecom.model.Product;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();

    List<ProductResponse> searchByName(String query);
    List<ProductResponse> filterProducts(Long categoryId,
                                         Double minPrice,
                                         Double maxPrice,
                                         Boolean onlyInStock);

    List<ProductResponse> getProductsByCategory(Long categoryId);

    Product saveProduct(Product product);
    ProductResponse updateProduct(Long id, ProductRequest updated);

    void deleteProduct(Long id);

    ProductResponse mapToResponse(Product product);
}
