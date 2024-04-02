package com.example.ekart_backend.repositories;

import com.example.ekart_backend.entities.Product;
import com.example.ekart_backend.entities.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewsRepo extends JpaRepository<Reviews, Integer> {

    List<Reviews> findAllByProduct(Product product);

}
