package com.example.ekart_backend.services.serviceInterface;

import com.example.ekart_backend.entities.Cart;
import com.example.ekart_backend.payloads.CartDto;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CartService {

    public CartDto addProductToCart(Integer userId, Integer productId);
    public CartDto updateCart(Integer userId, Integer productId, Integer quantity);
    public void deleteProduct(Integer userId, Integer productId);

    public List<CartDto> getALlCartByUser(Integer userId) throws IOException;
    public List<CartDto> getAllCartByUserWithPagination(Integer userId, Integer pageIndex, Integer pageSize);
}
