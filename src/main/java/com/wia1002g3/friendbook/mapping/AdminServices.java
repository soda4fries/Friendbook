package com.wia1002g3.friendbook.mapping;


import com.wia1002g3.friendbook.entity.Post;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@RequiredArgsConstructor
public class AdminServices {

    private final UserRepository userRepository;

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

    @DeleteMapping("/users/{userId}/posts/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer userId, @PathVariable Integer postId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userOptional.get();

        // Check if the post exists in the user's posts
        Optional<Post> postOptional = user.getPosts().stream()
                .filter(post -> post.getId().equals(postId))
                .findFirst();

        if (postOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = postOptional.get();

        // Delete the post
        user.getPosts().remove(post);
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }

}
