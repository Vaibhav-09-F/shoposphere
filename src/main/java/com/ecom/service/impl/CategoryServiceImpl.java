package com.ecom.service.impl;

import com.ecom.dto.category.CategoryRequest;
import com.ecom.dto.category.CategoryResponse;
import com.ecom.exception.ResourceNotFoundException;
import com.ecom.model.Category;
import com.ecom.repository.CategoryRepository;
import com.ecom.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category saved = categoryRepository.save(category);

        return new CategoryResponse(saved.getId(), saved.getName(), saved.getDescription());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponse> responseList = new ArrayList<>();

        for (Category c : categories) {
            responseList.add(new CategoryResponse(c.getId(), c.getName(), c.getDescription()));
        }

        return responseList;
    }
}
