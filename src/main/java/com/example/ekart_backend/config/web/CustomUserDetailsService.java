package com.example.ekart_backend.config.web;

import com.example.ekart_backend.entities.User;
import com.example.ekart_backend.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepo userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = this.userRepo.findByUserEmail(username).get();

        return user;
    }
}
