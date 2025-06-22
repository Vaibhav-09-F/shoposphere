package com.ecom.dto.product;

import com.ecom.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;

    // ← NEW:
    private Long   categoryId;
    private String categoryName;

    public ProductResponse() {}

    public ProductResponse(Long id,
                           String name,
                           String description,
                           Double price,
                           Integer stock,
                           Long categoryId,
                           String categoryName) {
        this.id           = id;
        this.name         = name;
        this.description  = description;
        this.price        = price;
        this.stock        = stock;
        this.categoryId   = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }

    public void setPrice(Double price) { this.price = price; }

    public Integer getStock() { return stock; }

    public void setStock(Integer stock) { this.stock = stock; }

    public Long getCategoryId() { return categoryId; }

    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
}
