package com.ecom.service;

import com.ecom.dto.user.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO addAddress(Long userId, AddressDTO dto);
    List<AddressDTO> getUserAddresses(Long userId);
    void deleteAddress(Long addressId);
}
