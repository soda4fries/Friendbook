package com.wia1002g3.friendbook.mapping;


import com.wia1002g3.friendbook.DTOs.PostDTO;
import com.wia1002g3.friendbook.entity.Post;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.PostRepository;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class AdminController {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @DeleteMapping("admin/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();

        // Delete the user
        userRepository.delete(user);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("admin/DeletePosts/{postId}")
    public ResponseEntity<Boolean> deletePost(@PathVariable Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        postRepository.delete(post);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
