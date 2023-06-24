package com.wia1002g3.friendbook.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String DOB;
    private String address;
    private String phoneNumber;
    private Boolean gender;
    private String bio;
    private String hobbies;
    private String relationStatus;
    private String jobExperiance;
    private String role;
}
