package com.ecom.controller;

import com.ecom.dto.user.AddressDTO;
import com.ecom.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/add")
    public AddressDTO addAddress(@RequestParam Long userId, @RequestBody AddressDTO dto) {
        return addressService.addAddress(userId, dto);
    }

    @GetMapping("/{userId}")
    public List<AddressDTO> getAddresses(@PathVariable Long userId) {
        return addressService.getUserAddresses(userId);
    }

    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
    }
}
