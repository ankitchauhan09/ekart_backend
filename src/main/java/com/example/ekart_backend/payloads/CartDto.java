package com.example.ekart_backend.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    Integer cartId;
    ProductDto product;
    Integer quantity;
//    UserDto user;
}
