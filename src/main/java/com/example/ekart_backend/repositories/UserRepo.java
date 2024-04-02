package com.example.ekart_backend.repositories;

import com.example.ekart_backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    Optional<User> findByUserEmail(String userEmail);

}
