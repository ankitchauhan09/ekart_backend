package com.example.ekart_backend.controller;

import com.example.ekart_backend.entities.Product;
import com.example.ekart_backend.exceptions.ApiResponse;
import com.example.ekart_backend.payloads.ImageResponse;
import com.example.ekart_backend.payloads.ProdCount;
import com.example.ekart_backend.payloads.ProductDto;
import com.example.ekart_backend.services.servicesImpl.ProductServiceImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;



//    @PostMapping("/category/{categoryId}")
//    public ResponseEntity<?> createProduct(@PathVariable("categoryId")Integer categoryId, @RequestBody ProductDto productDto) {
//        try {
//            return new ResponseEntity<>(this.productService.createProduct(productDto, categoryId), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
//        }
//    }
    @PostMapping("/category/{categoryId}")
    public ResponseEntity<?> createProduct(@PathVariable("categoryId")Integer categoryId, @ModelAttribute ProductDto productDto, @RequestParam("file") MultipartFile[] files) {
        try {
            ProductDto product = this.productService.createProductWithImages(categoryId, productDto, files);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") Integer productId, @RequestBody ProductDto productDto) {
        try {
            return new ResponseEntity<>(this.productService.updateProduct(productDto, productId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Integer productId) {
        try {
            this.productService.deleteProduct(productId);
            return new ResponseEntity<>(new ApiResponse("Product deleted successfully", true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Integer productId) {
        try {
            return new ResponseEntity<>(this.productService.getSingleProduct(productId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<?> getAllPosts(@PathVariable("pageNumber") Integer pageIndex, @PathVariable("pageSize") Integer pageSize, @RequestParam(value = "keyword", defaultValue = "")String keyword) {
        try {
            System.out.println("this is pageSize + " + pageSize);
            List<ProductDto> dtoList = this.productService.getAllProducts(pageIndex, pageSize, keyword);

            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/{productId}/upload-image")
    public ResponseEntity<?> uploadProductImages(@RequestParam("file") MultipartFile file, @PathVariable("productId") Integer productId){
        try{
            String imageName = this.productService.uploadProductImage(file, productId);
            return new ResponseEntity<>(new ApiResponse("Image uploaded successfully", true), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{productId}/get-image")
    public ResponseEntity<?> getProductImages(@PathVariable("productId") Integer productId){
        try{
            List<ImageResponse> images = this.productService.getAllImages(productId);
            System.out.println(images);
            return ResponseEntity.ok().body(images);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> getProductCount(){
        try{

            return new ResponseEntity<>(new ProdCount(this.productService.countProduct()), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
