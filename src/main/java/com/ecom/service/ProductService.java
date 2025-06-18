package com.ecom.service;

import com.ecom.dto.product.ProductRequest;
import com.ecom.dto.product.ProductResponse;
import com.ecom.model.Product;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();

    List<Product> searchByName(String query);
    List<Product> filterProducts(Long categoryId, Double minPrice, Double maxPrice, Boolean onlyInStock);

    Product saveProduct(Product product);
    Product updateProduct(Long id, Product updatedProduct);

    List<Product> getProductsByCategory(Long categoryId);

    void deleteProduct(Long id);

}
