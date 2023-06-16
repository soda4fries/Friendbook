package com.wia1002g3.friendbook.security;

import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import com.wia1002g3.friendbook.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Optional;

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
        userName =  request.getUsername();

        /*
        if (request.getCredtype().equals("USERNAME")) {
            userName =  request.getUsername();
        }


        else {

            Optional<User> userOptional = userRepository.findByEmailOrPhoneNumber(request.getCred());
            if (userOptional.isEmpty()) {
                // logic for sending phone or email not registered
            }
            User user = userOptional.get();
            userName = user.getUsername();
        }
         */

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

    public User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
