package com.example.ekart_backend.services.serviceInterface;

import com.example.ekart_backend.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    public CategoryDto createCategory(CategoryDto categoryDto);
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
    public void deleteCategory(Integer categoryId);
    public void deleteCategory(CategoryDto userDto);
    public CategoryDto getSingleCategory(Integer categoryId);
    public List<CategoryDto> getAllCategories();
}
