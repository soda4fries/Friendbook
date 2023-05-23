package com.wia1002g3.friendbook.mapping;


import com.wia1002g3.friendbook.security.AuthenticationResponse;
import com.wia1002g3.friendbook.security.AuthenticationService;
import com.wia1002g3.friendbook.security.RegisterRequest;
import com.wia1002g3.friendbook.security.loginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService Authservice;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(Authservice.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody loginRequest request) {
        return ResponseEntity.ok(Authservice.login(request));
    }

}
