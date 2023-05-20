package com.wia1002g3.friendbook.mapping;


import com.wia1002g3.friendbook.security.AuthenticationResponse;
import com.wia1002g3.friendbook.security.RegisterRequest;
import com.wia1002g3.friendbook.security.loginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return null;
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody loginRequest request) {
        return null;
    }
}
