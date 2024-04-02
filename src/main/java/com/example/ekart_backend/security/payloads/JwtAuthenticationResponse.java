package com.example.ekart_backend.security.payloads;

import com.example.ekart_backend.payloads.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {
    String token;
    UserDto user;
}
