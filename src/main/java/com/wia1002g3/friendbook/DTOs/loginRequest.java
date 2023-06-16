package com.wia1002g3.friendbook.DTOs;

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
    private String credType; //CRED_USERNAME
    private String password;
}