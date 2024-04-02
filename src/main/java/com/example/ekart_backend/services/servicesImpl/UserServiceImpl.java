package com.example.ekart_backend.services.servicesImpl;

import com.example.ekart_backend.entities.Cart;
import com.example.ekart_backend.entities.Role;
import com.example.ekart_backend.entities.User;
import com.example.ekart_backend.payloads.CartDto;
import com.example.ekart_backend.payloads.RoleDto;
import com.example.ekart_backend.payloads.UserDto;
import com.example.ekart_backend.repositories.CartRepo;
import com.example.ekart_backend.repositories.RoleRepo;
import com.example.ekart_backend.repositories.UserRepo;
import com.example.ekart_backend.services.serviceInterface.CartService;
import com.example.ekart_backend.services.serviceInterface.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerUser(UserDto userDto) {
        Role role = this.roleRepo.findByRoleName(userDto.getRoleName()).get();
        User user = this.modelMapper.map(userDto, User.class);
        System.out.println(user);
        System.out.println(role);
        System.out.println(userDto.getRole());
        user.setUserRealName(userDto.getUserRealName());
        System.out.println(user.getRole());
        user.getRole().add(role);
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        User savedUser = this.userRepo.save(user);
        System.out.println(savedUser);
        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        User saved = this.userRepo.save(user);
        return this.modelMapper.map(saved, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).get();
        user.setUserRealName(userDto.getUserRealName());
        user.setUserEmail(userDto.getUserEmail());
        user.setUserPassword(userDto.getUserPassword());
        user.setRole((Set<Role>) userDto.getRole().stream().map((roles) -> this.modelMapper.map(roles, Role.class)).toList());
        User saved = this.userRepo.save(user);
        return this.modelMapper.map(saved, UserDto.class);
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById((userId)).get();
        this.userRepo.delete(user);
    }

    @Override
    public void deleteUser(UserDto userDto) {
        this.userRepo.delete(this.modelMapper.map(userDto, User.class));
    }

    @Override
    public UserDto getSingleUser(Integer userId) {
        User user = this.userRepo.findById(userId).get();
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        List<CartDto> cartDtoList = user.getCart().stream().map((cart) -> this.modelMapper.map(cart, CartDto.class)).toList();
        userDto.setCart(cartDtoList);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> allUsers = this.userRepo.findAll();
        return allUsers.stream().map(user -> this.modelMapper.map(user, UserDto.class)).toList();
    }

    @Override
    public UserDto getUserByEmail(String username) {
        User user = this.userRepo.findByUserEmail(username).get();
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        List<CartDto> cartDtoList = user.getCart().stream().map((cart)->this.modelMapper.map(cart, CartDto.class)).collect(Collectors.toList());
        userDto.setCart(cartDtoList);
        return userDto;
    }

}
