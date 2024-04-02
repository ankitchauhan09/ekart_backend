package com.example.ekart_backend.controller;


import com.example.ekart_backend.exceptions.ApiResponse;
import com.example.ekart_backend.payloads.CartDto;
import com.example.ekart_backend.services.serviceInterface.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin()
public class CartController {

    @Autowired
    private CartService cartService;

    @PutMapping("/user/{userId}")
    public ResponseEntity<?> addToCartWithQuantity(@PathVariable("userId") Integer userId, @RequestParam("productId") Integer productId, @RequestParam("productQuantity") Integer quantity) {
        try {
            CartDto returnedCart = this.cartService.updateCart(userId, productId, quantity);
            System.out.println(returnedCart);
            return new ResponseEntity<>(returnedCart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> addToCart(@PathVariable("userId") Integer userId, @RequestParam("productId") Integer productId) {
        try {
            CartDto returnedCart = this.cartService.addProductToCart(userId, productId);
            System.out.println(returnedCart);
            return new ResponseEntity<>(returnedCart, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/user/{userId}/product/{productId}")
    public ResponseEntity<?> deleteCart(@PathVariable("userId") Integer userId, @PathVariable("productId") Integer productId) {
        try {
            this.cartService.deleteProduct(userId, productId);
            return new ResponseEntity<>(new ApiResponse("Cart Item Deleted Successfully", true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getAllCartItems(@PathVariable("userId") Integer userId) {
        try {
            return new ResponseEntity<>(this.cartService.getALlCartByUser(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/{userId}/{pageIndex}/{pageSize}")
    public ResponseEntity<?> getAllCartItemsWithPagination(@PathVariable("userId") Integer userId, @PathVariable("pageIndex") Integer pageIndex, @PathVariable("pageSize") Integer pageSize) {
        try {
            List<CartDto> cartDtoList = this.cartService.getAllCartByUserWithPagination(userId, pageIndex, pageSize);
            return new ResponseEntity<>(cartDtoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


}
