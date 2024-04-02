package com.example.ekart_backend.controller;

import com.example.ekart_backend.exceptions.ApiResponse;
import com.example.ekart_backend.payloads.AddressDto;
import com.example.ekart_backend.payloads.UserDto;
import com.example.ekart_backend.repositories.CartRepo;
import com.example.ekart_backend.services.serviceInterface.AddressService;
import com.example.ekart_backend.services.servicesImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private AddressService addressService;

    @PostMapping("/")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto usersDto, BindingResult bindingResult){
        try {
            return new ResponseEntity<>(this.userService.createUser(usersDto), HttpStatus.OK);
        }
        catch (Exception e){
            if(e.getMessage().contains("Duplicate")){
                return new ResponseEntity<>(new ApiResponse("Duplicate entry for email : " + usersDto.getUserEmail(), false), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserbyId(@PathVariable("userId") Integer userId){
        try{
            return new ResponseEntity<>(this.userService.getSingleUser(userId), HttpStatus.OK);
        }
        catch (Exception e){
            if(e.getMessage().contains("No value present")){
                return new ResponseEntity<>(new ApiResponse("No user found for user id  : " + userId, false), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(new ApiResponse(e.getMessage(),false), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers(){
        try{
            return new ResponseEntity<>(this.userService.getAllUser(), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") Integer userId, @RequestBody UserDto userDto){
        try{
            return new ResponseEntity<>(this.userService.updateUser(userDto, userId), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
        try{
            this.userService.deleteUser(userId);
            return new ResponseEntity<>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{userId}/address/add-address")
    public ResponseEntity<?> addNewAddress(@PathVariable("userId") Integer userId,@RequestBody AddressDto addressDto){
        try{
            AddressDto insertedAddress = this.addressService.addAddress(userId, addressDto);
            return new ResponseEntity<>(insertedAddress, HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}/address/get-address")
    public ResponseEntity<?> getAddress(@PathVariable("userId") Integer userId){
        try{
            List<AddressDto> addressDto = this.addressService.getAddresses(userId);
            return new ResponseEntity<>(addressDto, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
    }


}
