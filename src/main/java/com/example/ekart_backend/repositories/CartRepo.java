package com.example.ekart_backend.repositories;

import com.example.ekart_backend.entities.Cart;
import com.example.ekart_backend.entities.Product;
import com.example.ekart_backend.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Integer> {

    Cart findByUserAndProduct(User user, Product product);

    List<Cart> findAllByUser(User user);

    Page<Cart> findAllByUser(User user, Pageable pageable);

}
