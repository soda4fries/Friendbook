package com.wia1002g3.friendbook.services;

import com.wia1002g3.friendbook.entity.Role;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import com.wia1002g3.friendbook.DTOs.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServices {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public String ConvertUserIDtoUserName(Integer userID) throws Exception{
        return userRepository.findById(userID).orElseThrow(()-> new Exception("User not found")).getUsername();
    }

    public String[] ConvertUserIDstoUserNames(Integer[] userIDs) {
        return Arrays.stream(userIDs).
                map(id -> {
                    try {
                        return ConvertUserIDtoUserName(id);
                    } catch (Exception e) {
                        return "INVALID_USER";
                    }
                })
                .toArray(String[]::new);
    }

    public User createUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            System.out.println("User Already exist");
            throw new RuntimeException("Username already exists");
        }
        System.out.println("Singleton graph works");
        Role role = (request.getIsAdminrole() == 1) ? Role.ADMIN : Role.USER;
        System.out.println("Role works");
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .role(role)
                .friends(new ArrayList<User>())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .address(request.getAddress())
                .Birthday(request.getDateOfBirth())
                .hobbies(getHobbiesAsArrayList(request.getHobbies()))
                .jobExperiences(getExperianceAsArrayList(request.getJobexperiances()))
                .build();
        System.out.println("User builder works");
        userRepository.save(user);
        System.out.println("user Repo works");
        System.out.println("Friendship graph works");
        return user;
    }



    private ArrayList<String> getHobbiesAsArrayList(String hobbies) {
        String[] hobbyArr = hobbies.split(",");
        ArrayList<String> hobbiesVector = new ArrayList<>();
        Collections.addAll(hobbiesVector, hobbyArr);
        return hobbiesVector;
    }

    private Stack<String> getExperianceAsArrayList(String jobExperiance) {
        String[] jobExperiances = jobExperiance.split(",");
        Stack<String> JobsStact = new Stack<>();
        for (String experiance : jobExperiances) {
            JobsStact.push(experiance);
        }
        return JobsStact;
    }
}