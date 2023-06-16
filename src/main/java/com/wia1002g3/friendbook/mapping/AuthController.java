package com.wia1002g3.friendbook.mapping;


import com.wia1002g3.friendbook.repository.UserRepository;
import com.wia1002g3.friendbook.DTOs.AuthenticationResponse;
import com.wia1002g3.friendbook.services.AuthenticationService;
import com.wia1002g3.friendbook.DTOs.RegisterRequest;
import com.wia1002g3.friendbook.DTOs.loginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService Authservice;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        System.out.println("PostMapping worked");
        try {
            AuthenticationResponse response = Authservice.register(request);
            System.out.println("Register worked");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.out.println("Something went wrong worked");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody loginRequest request) {
        return ResponseEntity.ok(Authservice.login(request));
    }

    @GetMapping("/registerVerify/userNameTaken/{username}")
    public boolean UserNameTaken(@PathVariable String username){
        return userRepository.existsByUsername(username);
    }

}
