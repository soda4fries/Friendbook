package com.wia1002g3.friendbook.mapping;

import com.wia1002g3.friendbook.DTOs.PostDTO;
import com.wia1002g3.friendbook.DTOs.PostUpload;
import com.wia1002g3.friendbook.DTOs.UploadPostReq;
import com.wia1002g3.friendbook.entity.Post;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.PostRepository;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class HomePageController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @PostMapping("api/HomePage/post/{userid}")
    public boolean CreatePost(@PathVariable Integer userid, @RequestBody PostUpload postupload) {
        User user = userRepository.findById(userid).orElseThrow();
        Post newPost = new Post();
        newPost.setPoster(user);
        newPost.setTimestamp(new Date());
        newPost.setCaption(postupload.getCaption());
        newPost.setImageBase64(postupload.getBase64image());
        user.getPosts().add(newPost);
        postRepository.save(newPost);
        userRepository.save(user);
        return true;
    }

    @GetMapping("api/HomePage/GetHomePost/{userid}")
    public ResponseEntity<ArrayList<PostDTO>> getHomePagePost(@PathVariable("userid") Integer userid) {
        User user = userRepository.findById(userid).orElseThrow();
        ArrayList<Post> allPost = new ArrayList<>();
        for (User friend : user.getFriends()) {
            allPost.addAll(friend.getPosts());
        }
        ArrayList<PostDTO> posts = new ArrayList<>();
        for (Post post: allPost) {
            posts.add(new PostDTO(post.getId(), post.getCaption(), post.getImageBase64(), post.getLikes(), post.getTimestamp()));
        }

        return ResponseEntity.ok(posts);
    }



    @GetMapping("api/HomePage/GetPost/{userid}")
    public ResponseEntity<ArrayList<PostDTO>> getPostById(@PathVariable("userid") Integer userid) {
        User user = userRepository.findById(userid).orElseThrow();
        ArrayList<PostDTO> posts = new ArrayList<>();

        for(int i = 0; i < user.getPosts().size(); i++) {
            Post post = user.getPosts().get(i);
            PostDTO postDTO = new PostDTO(post.getId(), post.getCaption(), post.getImageBase64(), post.getLikes(), post.getTimestamp());
            posts.add(postDTO);
        }
        Collections.sort(posts);
        return ResponseEntity.ok(posts);
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

    @PostMapping("api/HomePage/UploadPost/{userid}")
    public boolean uploadPost(@PathVariable Integer userid, @RequestBody UploadPostReq request) {
        User user = userRepository.findById(userid).orElseThrow();
        Post post = new Post();
        post.setLikes(new ArrayList<>());
        post.setCaption(request.getCaption());
        post.setImageBase64(request.getBase64image());
        post.setTimestamp(new Date());

        user.getPosts().add(post);
        postRepository.save(post);
        userRepository.save(user);
        return true;
    }
    @PostMapping("api/HomePage/LikePost/{userid}/{postid}")
    public boolean likePost(@PathVariable Integer userid, @PathVariable Integer postid) {
        User user = userRepository.findById(userid).orElseThrow();
        Post post = postRepository.findById(postid).orElseThrow();
        post.getLikes().add(user);

        postRepository.save(post);
        return true;
    }
}
