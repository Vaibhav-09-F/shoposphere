package com.ecom.service;

import com.ecom.dto.user.UserRequest;
import com.ecom.dto.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
}
