package com.wia1002g3.friendbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
public class FriendBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendBookApplication.class, args);
    }

}
