package com.example.ekart_backend.repositories;

import com.example.ekart_backend.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    Page<Product> findAllByProductNameContainingIgnoreCase(String productName, Pageable pageable);

    List<String> getAllImagesById(Integer productId);

}
