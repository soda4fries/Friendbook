package com.wia1002g3.friendbook.mapping;


import com.wia1002g3.friendbook.security.AuthenticationResponse;
import com.wia1002g3.friendbook.security.AuthenticationService;
import com.wia1002g3.friendbook.security.RegisterRequest;
import com.wia1002g3.friendbook.security.loginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService Authservice;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse response = Authservice.register(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Handle the exception and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody loginRequest request) {
        return ResponseEntity.ok(Authservice.login(request));
    }

    @GetMapping("/registerVerify/userNameTaken/{username}")
    public boolean UserNameTaken(@PathVariable String username){
        return Authservice.IsUserNameTaken(username);
    }

}
