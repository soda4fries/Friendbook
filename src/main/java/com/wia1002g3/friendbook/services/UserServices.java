package com.wia1002g3.friendbook.services;

import com.wia1002g3.friendbook.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServices {
    private UserRepository repo;

    private String ConvertUserIDtoUserName(Integer userID) throws Exception{
        return repo.findById(userID).orElseThrow(()-> new Exception("User not found")).getUsername();
    }

    private String[] ConvertUserIDtoUserName(Integer[] userIDs) throws Exception {
        return Arrays.stream(userIDs).
                map(id -> {
                    try {
                        return ConvertUserIDtoUserName(userIDs);
                    } catch (Exception e) {
                        return "INVALID_USER";
                    }
                })
                .toArray(String[]::new);
    }

}
