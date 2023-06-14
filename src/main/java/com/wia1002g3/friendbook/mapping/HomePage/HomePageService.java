package com.wia1002g3.friendbook.mapping.HomePage;

import com.wia1002g3.friendbook.entity.Post;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequiredArgsConstructor
public class HomePageService {

    private final UserRepository userRepository;

    @GetMapping("/Api/post/GetAllPostFromFriends/{userID}")
    public ResponseEntity<ArrayList<Post>> getAllPost(@PathVariable Integer userID) {
        User user = userRepository.findById(userID).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ArrayList<Post> posts = user.getPosts(); // Retrieve posts for the user
        return ResponseEntity.ok(posts);
    }






}
