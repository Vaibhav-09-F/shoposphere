package com.ecom.service;

import com.ecom.dto.product.ProductRequest;
import com.ecom.dto.product.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
}
