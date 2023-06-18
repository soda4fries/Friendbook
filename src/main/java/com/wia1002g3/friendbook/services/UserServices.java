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

    public User createUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        Role role = (request.getIsAdminrole() == 1) ? Role.ADMIN : Role.USER;
        System.out.println(request.toString());
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .role(role)
                .bio(request.getBio())
                .friends(new ArrayList<>())
                .viewedPost(new LinkedList<>())
                .posts(new ArrayList<>())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .relationStatus(request.getRelationshipStatus())
                .address(request.getAddress())
                .Birthday(request.getDateOfBirth())
                .hobbies(getHobbiesAsArrayList(request.getHobbies()))
                .jobExperiences(getExperianceAsList(request.getJobExperiance()))
                .build();
        userRepository.save(user);
        return user;
    }



    private ArrayList<String> getHobbiesAsArrayList(String hobbies) {
        String[] hobbyArr = hobbies.split(",");
        ArrayList<String> hobbiesVector = new ArrayList<>();
        Collections.addAll(hobbiesVector, hobbyArr);
        return hobbiesVector;
    }

    private Stack<String> getExperianceAsList(String jobExperiance) {
        String[] jobExperiances = jobExperiance.split(",");
        Stack<String> JobsStact = new Stack<>();
        for (String experiance : jobExperiances) {
            JobsStact.push(experiance);
        }
        return JobsStact;
    }
}