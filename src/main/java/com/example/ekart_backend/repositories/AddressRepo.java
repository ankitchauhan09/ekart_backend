package com.example.ekart_backend.repositories;

import com.example.ekart_backend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo extends JpaRepository<Address, Integer> {
}
