    package com.example.ekart_backend.services.servicesImpl;

    import com.example.ekart_backend.entities.Address;
    import com.example.ekart_backend.entities.User;
    import com.example.ekart_backend.payloads.AddressDto;
    import com.example.ekart_backend.repositories.AddressRepo;
    import com.example.ekart_backend.repositories.UserRepo;
    import com.example.ekart_backend.services.serviceInterface.AddressService;
    import org.modelmapper.ModelMapper;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.ui.ModelMap;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class AddressServiceImpl implements AddressService {

        @Autowired
        private UserRepo userRepo;
        @Autowired
        private AddressRepo addressRepo;
        @Autowired
        private ModelMapper modelMapper;

        @Override
        public AddressDto addAddress(Integer userId, AddressDto addressDto) {

            User user = this.userRepo.findById(userId).get();
            Address address = this.modelMapper.map(addressDto, Address.class);
            address.setUser(user);
            Address savedAddress = this.addressRepo.save(address);
            return this.modelMapper.map(savedAddress, AddressDto.class);
        }

        @Override
        public List<AddressDto> getAddresses(Integer userId) {
            User user = this.userRepo.findById(userId).get();
            List<Address> allAddresses = user.getAddress();
            List<AddressDto> addressDtoList = allAddresses.stream().map((address) -> this.modelMapper.map(address, AddressDto.class)).collect(Collectors.toList());
            return addressDtoList;
        }
    }
