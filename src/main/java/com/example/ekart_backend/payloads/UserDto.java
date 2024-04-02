package com.example.ekart_backend.payloads;

import com.example.ekart_backend.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class    UserDto {

    private Integer id;
    private String userRealName;
    private String userEmail;
    private String userPassword;
    private String userContact;
    private String userRecoveryEmail;
    private String roleName;
    private Set<Role> role = new HashSet<>();
    private List<CartDto> cart = new ArrayList<>();
    private List<AddressDto> address = new ArrayList<>();

}
