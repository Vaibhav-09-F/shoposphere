package com.ecom.controller;

import com.ecom.dto.product.ProductRequest;
import com.ecom.dto.product.ProductResponse;
import com.ecom.model.Product;
import com.ecom.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Maps a Product entity (with its Category) to a ProductResponse DTO.
     */
    private ProductResponse mapToResponse(Product p) {
        Long catId   = null;
        String catNm = null;
        if (p.getCategory() != null) {
            catId   = p.getCategory().getId();
            catNm   = p.getCategory().getName();
        }
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

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    // in ProductController.java
    @GetMapping("/search")
    public List<ProductResponse> searchProducts(@RequestParam String query) {
        return productService.searchByName(query);
    }


    @GetMapping("/filter")
    public List<ProductResponse> filterProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean onlyInStock
    ) {
        return productService.filterProducts(categoryId, minPrice, maxPrice, onlyInStock);
    }

    @PostMapping("/admin/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/update/{id}")
    public ProductResponse updateProduct(@PathVariable Long id,
                                         @RequestBody ProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/category/{id}")
    public List<ProductResponse> byCategory(@PathVariable("id") Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }
}