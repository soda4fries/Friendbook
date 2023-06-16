package com.wia1002g3.friendbook.services;

import com.wia1002g3.friendbook.entity.FriendshipGraph;
import com.wia1002g3.friendbook.entity.Role;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.FriendshipGraphRepository;
import com.wia1002g3.friendbook.repository.UserRepository;
import com.wia1002g3.friendbook.security.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

@Service
@RequiredArgsConstructor
public class UserServices {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FriendshipGraphService friendshipGraphService;
    private final FriendshipGraphRepository friendshipGraphRepository;

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
            throw new RuntimeException("Username already exists");
        }
        FriendshipGraph graph = friendshipGraphService.getSingletonFriendshipGraph();
        Role role = (request.getIsAdminrole() == 1) ? Role.ADMIN : Role.USER;
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .graphID(graph.addUser())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .gender(request.getGender())
                .address(request.getAddress())
                .age(request.getAge())
                .hobbies(getHobbiesAsArrayList(request.getHobbies()))
                .jobExperiences(getExperianceAsArrayList(request.getJobexperiances()))
                .build();
        userRepository.save(user);
        friendshipGraphRepository.save(graph);
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