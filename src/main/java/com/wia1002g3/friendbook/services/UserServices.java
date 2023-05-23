package com.wia1002g3.friendbook.services;

import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UserServices {
    private final UserRepository userRepository;

    private String ConvertUserIDtoUserName(Integer userID) throws Exception{
        return userRepository.findById(userID).orElseThrow(()-> new Exception("User not found")).getUsername();
    }

    private String[] ConvertUserIDstoUserNames(Integer[] userIDs) {
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

}
