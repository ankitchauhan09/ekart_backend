package com.example.ekart_backend.services.servicesImpl;

import com.example.ekart_backend.entities.Cart;
import com.example.ekart_backend.entities.Product;
import com.example.ekart_backend.entities.User;
import com.example.ekart_backend.payloads.CartDto;
import com.example.ekart_backend.payloads.ImageResponse;
import com.example.ekart_backend.repositories.CartRepo;
import com.example.ekart_backend.repositories.ProductRepo;
import com.example.ekart_backend.repositories.UserRepo;
import com.example.ekart_backend.services.serviceInterface.CartService;
import com.example.ekart_backend.services.serviceInterface.FileUploadService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FileUploadService fileUploadService;

    @Value("${project.image}")
    private String path;

    @Override
    public CartDto addProductToCart(Integer userId, Integer productId) {
        Product product = this.productRepo.findById(productId).get();
        Cart cart = new Cart();
        cart.setUser(this.userRepo.findById(userId).get());
        cart.setProduct(this.productRepo.findById(productId).get());
        cart.setQuantity(1);
        return this.modelMapper.map(this.cartRepo.save(cart), CartDto.class);    }

    @Override
    public CartDto updateCart(Integer userId, Integer productId, Integer quantity) {
        Product product = this.productRepo.findById(productId).get();
        User user = this.userRepo.findById(userId).get();
        Cart cart = this.cartRepo.findByUserAndProduct(user, product);
        cart.setQuantity(quantity);
        return this.modelMapper.map(this.cartRepo.save(cart), CartDto.class);
    }

    @Override
    public void deleteProduct(Integer userId, Integer productId) {
        User user = this.userRepo.findById(userId).get();
        Product product = this.productRepo.findById(productId).get();
        Cart cart = this.cartRepo.findByUserAndProduct(user, product);
        this.cartRepo.delete(cart);
    }

    @Override
    public List<CartDto> getALlCartByUser(Integer userId) throws IOException {
        User user = this.userRepo.findById(userId).get();
        List<Cart> allItems = this.cartRepo.findAllByUser(user);
        List<CartDto> output = allItems.stream().map(cart -> this.modelMapper.map(cart, CartDto.class)).collect(Collectors.toList());
        for(CartDto cartItems : output){
            List<ImageResponse> image = new ArrayList<>();
            for(String images : cartItems.getProduct().getProductImage()){
                InputStream in = this.fileUploadService.getImage(path, images);
                image.add(new ImageResponse(images, in.readAllBytes()));
            }
            cartItems.getProduct().setImages(image);
        }
        return output;
    }

    @Override
    public List<CartDto> getAllCartByUserWithPagination(Integer userId, Integer pageIndex, Integer pageSize) {
        User user = this.userRepo.findById(userId).get();
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        List<Cart> cartItems = this.cartRepo.findAllByUser(user, pageable).getContent();
        return cartItems.stream().map(cart -> this.modelMapper.map(cart, CartDto.class)).toList();
    }
}
