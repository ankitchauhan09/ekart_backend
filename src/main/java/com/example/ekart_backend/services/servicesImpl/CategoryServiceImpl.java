package com.example.ekart_backend.services.servicesImpl;

import com.example.ekart_backend.entities.Category;
import com.example.ekart_backend.payloads.CategoryDto;
import com.example.ekart_backend.repositories.CategoryRepo;
import com.example.ekart_backend.services.serviceInterface.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category savedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).get();
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category savedCategory = this.categoryRepo.save(category);
        return this.modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).get();
        this.categoryRepo.delete(category);
    }

    @Override
    public void deleteCategory(CategoryDto categoryDto) {
        this.categoryRepo.delete(this.categoryRepo.findById(categoryDto.getId()).get());
    }

    @Override
    public CategoryDto getSingleCategory(Integer categoryId) {
        return this.modelMapper.map(this.categoryRepo.findById(categoryId).get(), CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> allCategories = this.categoryRepo.findAll();
        return allCategories.stream().map(category -> this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }
}
