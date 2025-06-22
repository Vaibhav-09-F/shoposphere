package com.ecom.service.impl;

import com.ecom.dto.product.ProductRequest;
import com.ecom.dto.product.ProductResponse;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.model.Category;
import com.ecom.model.Product;
import com.ecom.repository.CategoryRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        // 1) fetch & validate the category
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        // 2) build the product
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);              // ← link it!

        // 3) save & map back
        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

        Category category = product.getCategory();
        Long categoryId = (category != null) ? category.getId() : null;
        String categoryName = (category != null) ? category.getName() : null;

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                categoryId,
                categoryName
        );
    }

    @Override
    @Transactional
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> responses = new ArrayList<>();

        for (Product product : products) {
            Category category = product.getCategory();
            Long categoryId = (category != null) ? category.getId() : null;
            String categoryName = (category != null) ? category.getName() : null;

            responses.add(new ProductResponse(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock(),
                    categoryId,
                    categoryName
            ));
        }
        return responses;
    }


    @Override
    public List<ProductResponse> searchByName(String query) {
        return productRepository
                .findByNameContainingIgnoreCase(query)
                .stream()
                .map(this::mapToResponse)    // mapping happens inside @Transactional
                .toList();
    }


    @Override
    @Transactional
    public List<ProductResponse> filterProducts(Long categoryId,
                                                Double minPrice,
                                                Double maxPrice,
                                                Boolean onlyInStock) {
        return productRepository
                .filterProducts(categoryId, minPrice, maxPrice, onlyInStock)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest updated) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());

        if (updated.getCategoryId() != null) {
            Category category = categoryRepository.findById(updated.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", updated.getCategoryId()));
            existing.setCategory(category);
        }

        Product saved = productRepository.save(existing);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public List<ProductResponse> getProductsByCategory(Long categoryId) {
        return productRepository
                .findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }

    public ProductResponse mapToResponse(Product p) {
        Long catId   = p.getCategory() != null ? p.getCategory().getId()   : null;
        String catNm = p.getCategory() != null ? p.getCategory().getName() : null;
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                p.getStock(),
                catId,
                catNm
        );
    }

}
