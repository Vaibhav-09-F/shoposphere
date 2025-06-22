// com.ecom.controller.AuthController.java
package com.ecom.controller;

import com.ecom.config.JwtUtil;
import com.ecom.dto.auth.*;
import com.ecom.model.Role;
import com.ecom.model.User;
import com.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserRepository userRepo;
    @Autowired private AuthenticationManager authManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;  // ← inject this

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));

        // ❗ assign role from payload if present, otherwise USER
        Role assigned = req.getRole() != null ? req.getRole() : Role.USER;
        u.setRole(assigned);

        userRepo.save(u);
        return "Registered successfully as " + assigned;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        // 1. Authenticate and capture the Authentication object
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 2. Pull the UserDetails from that Authentication
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Generate the JWT using the username (email)
        String token = jwtUtil.generateToken(userDetails.getUsername());

        // 4. Extract the role name (e.g. "ROLE_USER" or "ROLE_ADMIN")
        String role = userDetails.getAuthorities()
                .stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");

        // 5. (Optionally) set the SecurityContext so downstream filters see you as authenticated
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 6. Return the token and role
        return new AuthResponse(token, role);
    }

}
