package com.wia1002g3.friendbook.services;

import com.wia1002g3.friendbook.entity.FriendshipGraph;
import com.wia1002g3.friendbook.entity.Role;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.FriendshipGraphRepository;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

    public User createUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        FriendshipGraph graph = friendshipGraphService.getSingletonFriendshipGraph();
        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .graphID(graph.addUser())
                .build();
        userRepository.save(user);
        friendshipGraphRepository.save(graph);
        return user;
    };
}