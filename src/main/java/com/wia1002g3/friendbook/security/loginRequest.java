package com.wia1002g3.friendbook.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class loginRequest {
    private String cred;
    private String username;
    private String credtype;
    private String password;
}