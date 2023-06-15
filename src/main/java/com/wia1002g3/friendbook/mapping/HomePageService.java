package com.wia1002g3.friendbook.mapping;

import com.wia1002g3.friendbook.entity.Post;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.PostRepository;
import com.wia1002g3.friendbook.repository.UserRepository;
import jakarta.persistence.Transient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class HomePageService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @GetMapping("api/HomePage/GetPost/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable("id") Integer postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            // Create a PostDTO with simplified data
            PostDTO postDTO = new PostDTO(post.getId(), post.getCaption(), post.getImageBase64(), post.getLikes());
            return ResponseEntity.ok(postDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public class PostDTO {
        private Integer id;
        private String caption;
        private String imageBase64;
        private ArrayList<Integer> likeUserIds;

        public PostDTO(Integer id, String caption, String imageBase64, ArrayList<User> likes) {
            this.id = id;
            this.caption = caption;
            this.imageBase64 = imageBase64;
            this.likeUserIds = new ArrayList<>();

            for (User user : likes) {
                this.likeUserIds.add(user.getId());
            }
        }
    }

    @PostMapping("api/HomePage/viewedPost/{userId}/{postId}")
    public ResponseEntity<Boolean> viewedPost(@PathVariable Integer postId, @PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        LinkedList<Integer> viewed = user.getViewedPost();
        viewed.add(postId);
        userRepository.save(user);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("api/HomePage/GetViewedPost/{userid}/")
    public ResponseEntity<List<Integer>> getViewedPost(@PathVariable Integer userid) {
        User user = userRepository.findById(userid).orElseThrow();
        LinkedList<Integer> viewed = user.getViewedPost();
        return ResponseEntity.ok(viewed);
    }

}
