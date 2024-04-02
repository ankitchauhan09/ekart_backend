package com.example.ekart_backend.services.serviceInterface;

import com.example.ekart_backend.payloads.AddressDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    public AddressDto addAddress(Integer userId, AddressDto address);

    public List<AddressDto> getAddresses(Integer userId);


}
