    package com.example.ekart_backend.controller;

    import com.example.ekart_backend.config.web.CustomUserDetailsService;
    import com.example.ekart_backend.entities.Role;
    import com.example.ekart_backend.exceptions.ApiException;
    import com.example.ekart_backend.exceptions.ApiResponse;
    import com.example.ekart_backend.payloads.UserDto;
    import com.example.ekart_backend.security.JwtTokenHelper;
    import com.example.ekart_backend.security.payloads.JwtAuthenticationRequest;
    import com.example.ekart_backend.security.payloads.JwtAuthenticationResponse;
    import com.example.ekart_backend.services.serviceInterface.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.BadCredentialsException;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.web.bind.annotation.*;
    import com.example.ekart_backend.entities.User;

    import java.util.List;
    import java.util.Set;

    @RestController
    @RequestMapping("/api/v1/auth/")
    @CrossOrigin
    public class AuthController {

        @Autowired
        private JwtTokenHelper jwtTokenHelper;

        @Autowired
        private CustomUserDetailsService userDetailsService;

        @Autowired
        private UserService userService;

        @Autowired
        private AuthenticationManager authenticationManager;

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody JwtAuthenticationRequest request){
            try{
                System.out.println(request.getUsername() + " " + request.getPassword());
                this.authenticate(request.getUsername(), request.getPassword());
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
                String token = this.jwtTokenHelper.generateToken(userDetails);
                JwtAuthenticationResponse response = new JwtAuthenticationResponse();
                response.setToken(token);
                response.setUser(this.userService.getUserByEmail(request.getUsername()));
                return new ResponseEntity<>(response, HttpStatus.OK);

            }
            catch (Exception e){
                return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
            }
        }

        private void authenticate(String username, String password){
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            try{
                this.authenticationManager.authenticate(authenticationToken);
            }
            catch (BadCredentialsException e){
                throw new ApiException("Invalid username or password");
            }
        }


        @PostMapping("/register")
        public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
            try{
                return new ResponseEntity<>(this.userService.registerUser(userDto), HttpStatus.OK);
            }
            catch (Exception e){
                return new ResponseEntity<>(new ApiResponse(e.getMessage(), false), HttpStatus.BAD_REQUEST);
            }
        }

    }
