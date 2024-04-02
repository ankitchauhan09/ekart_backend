package com.example.ekart_backend.controller;


import com.example.ekart_backend.exceptions.ApiResponse;
import com.example.ekart_backend.payloads.CategoryDto;
import com.example.ekart_backend.services.serviceInterface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {
        try {
            return new ResponseEntity<>(this.categoryService.createCategory(categoryDto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable("categoryId") Integer categoryId, @RequestBody CategoryDto categoryDto) {
        try {
            return new ResponseEntity<>(this.categoryService.updateCategory(categoryDto, categoryId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCateogory(@PathVariable("categoryId") Integer categoryId) {
        try {
            this.categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(new ApiResponse("Category Deleted Successfully", true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable("categoryId") Integer categoryId){
        try{
            return new ResponseEntity<>(this.categoryService.getSingleCategory(categoryId), HttpStatus.FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCategory(){
        try{
            return new ResponseEntity<>(this.categoryService.getAllCategories(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        }
    }

}
