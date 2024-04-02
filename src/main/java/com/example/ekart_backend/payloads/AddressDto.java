package com.example.ekart_backend.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private Integer id;
    private String street;
    private String locality;
    private String city;
    private String state;
    private Integer pincode;

}
