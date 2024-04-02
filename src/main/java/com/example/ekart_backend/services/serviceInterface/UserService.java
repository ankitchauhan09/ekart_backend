package com.example.ekart_backend.services.serviceInterface;

import com.example.ekart_backend.entities.Cart;
import com.example.ekart_backend.entities.Role;
import com.example.ekart_backend.entities.User;
import com.example.ekart_backend.payloads.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

public interface UserService {

    UserDto registerUser(UserDto userDto);
    UserDto createUser(UserDto userDto);

    public UserDto updateUser(UserDto userDto, Integer userId);
    public void deleteUser(Integer userId);
    public void deleteUser(UserDto userDto);
    public UserDto getSingleUser(Integer userId);
    public List<UserDto> getAllUser();

//    public List<Cart> getCart();

    public UserDto getUserByEmail(String username);

}
