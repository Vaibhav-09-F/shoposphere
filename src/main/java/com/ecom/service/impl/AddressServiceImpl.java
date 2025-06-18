package com.ecom.service.impl;

import com.ecom.dto.user.AddressDTO;
import com.ecom.model.Address;
import com.ecom.model.User;
import com.ecom.repository.AddressRepository;
import com.ecom.repository.UserRepository;
import com.ecom.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired private UserRepository userRepository;
    @Autowired private AddressRepository addressRepository;

    @Override
    public AddressDTO addAddress(Long userId, AddressDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = new Address();
        address.setUser(user);
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setPostalCode(dto.getPostalCode());

        Address saved = addressRepository.save(address);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public List<AddressDTO> getUserAddresses(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return addressRepository.findByUser(user).stream().map(a -> {
            AddressDTO dto = new AddressDTO();
            dto.setId(a.getId());
            dto.setStreet(a.getStreet());
            dto.setCity(a.getCity());
            dto.setState(a.getState());
            dto.setCountry(a.getCountry());
            dto.setPostalCode(a.getPostalCode());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }
}
