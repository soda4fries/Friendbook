package com.wia1002g3.friendbook.services;

import com.wia1002g3.friendbook.DTOs.AuthenticationResponse;
import com.wia1002g3.friendbook.DTOs.RegisterRequest;
import com.wia1002g3.friendbook.DTOs.loginRequest;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import com.wia1002g3.friendbook.security.JwtService;
import com.wia1002g3.friendbook.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserServices userServices;

    public AuthenticationResponse register(RegisterRequest request) {
        try {
            User user = userServices.createUser(request);
            String token = jwtService.generateToken(new LinkedHashMap<>(), user);
            System.out.println("did not fail to Generate token");
            return new AuthenticationResponse(token);
        } catch (RuntimeException e) {
            System.out.println(e.toString());
            throw new RuntimeException("Failed to register user");
        }
    }

    public AuthenticationResponse login(loginRequest request) {
        String userName;

        if (request.getCredType().equals("CRED_USERNAME")) {
            userName = request.getCred();
        } else {
            User user = userRepository.findByEmailOrPhoneNumber(request.getCred(), request.getCred()).orElseThrow();
            userName = user.getUsername();
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userName, request.getPassword()
                )
        );

        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(new LinkedHashMap<>(), user))
                .build();
    }
}
