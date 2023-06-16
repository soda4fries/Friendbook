package com.wia1002g3.friendbook.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;
    private String phoneNumber;
    private Boolean gender;
    private String bio;
    private String hobbies;
    private String jobexperiances;
    private Integer isAdminrole;
}
