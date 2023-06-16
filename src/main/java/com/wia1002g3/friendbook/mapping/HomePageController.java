package com.wia1002g3.friendbook.mapping;

import com.wia1002g3.friendbook.entity.FriendshipGraph;
import com.wia1002g3.friendbook.entity.Post;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.PostRepository;
import com.wia1002g3.friendbook.repository.UserRepository;
import com.wia1002g3.friendbook.services.FriendshipGraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class HomePageController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FriendshipGraphService friendshipGraphService;

    @PostMapping("api/HomePage/post/{userid}")
    public boolean CreatePost(@PathVariable Integer userid, @RequestBody postUpload postupload) {
        User user = userRepository.findById(userid).orElseThrow();
        Post newPost = new Post();
        newPost.setPoster(user);
        newPost.setTimestamp(new Date());
        newPost.setCaption(postupload.caption);
        newPost.setImageBase64(postupload.base64image);
        user.getPosts().add(newPost);
        postRepository.save(newPost);
        userRepository.save(user);
        return true;
    }

    private static class postUpload {
        String caption;
        String base64image;
    }

    @GetMapping("api/HomePage/GetHomePost/{userid}")
    public ResponseEntity<ArrayList<PostDTO>> getHomePagePost(@PathVariable("userid") Integer userid) {
        List<Integer> friendIds = getFriends(userid);
        ArrayList<PostDTO> posts = new ArrayList<>();
        for(int i = 0; i < friendIds.size(); i++) {
            User friend = userRepository.findById(friendIds.get(i)).orElseThrow();
            for(int j = 0; j < friend.getPosts().size(); j++) {
                Post post = friend.getPosts().get(i);
                PostDTO postDTO = new PostDTO(post.getId(), post.getCaption(), post.getImageBase64(), post.getLikes(), post.getTimestamp());
                posts.add(postDTO);
            }
        }
        return ResponseEntity.ok(posts);
    }

    public List<Integer> getFriends(Integer userid) {
        User user = userRepository.findById(userid).orElseThrow();
        Integer graphID = user.getGraphID();
        FriendshipGraph graph = friendshipGraphService.getSingletonFriendshipGraph();
        ArrayList<Integer> GraphFriends = graph.showFriends(graphID);
        ArrayList<Integer> friends = new ArrayList<>();
        for (Integer graphFriend : GraphFriends) {
            friends.add(userRepository.findByGraphID(graphFriend).orElseThrow().getId());
        }
        return friends;
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

    public static class PostDTO implements Comparable<PostDTO> {
        private Integer id;
        private String caption;
        private String imageBase64;
        private ArrayList<String> likeUser;

        private Date timestamp;

        public PostDTO(Integer id, String caption, String imageBase64, ArrayList<User> likes, Date timestamp) {
            this.id = id;
            this.caption = caption;
            this.imageBase64 = imageBase64;
            this.timestamp = timestamp;
            this.likeUser = new ArrayList<>();

            for (User user : likes) {
                this.likeUser.add(user.getUsername());
            }
        }

        @Override
        public int compareTo(PostDTO o) {
            return this.timestamp.compareTo(o.timestamp);
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

    //upload post
    public class UploadPostReq {
        String caption;
        String Base64image;
    }

    @PostMapping("api/HomePage/UploadPost/{userid}")
    public boolean uploadPost(@PathVariable Integer userid, @RequestBody UploadPostReq request) {
        User user = userRepository.findById(userid).orElseThrow();
        Post post = new Post();
        post.setLikes(new ArrayList<>());
        post.setCaption(request.caption);
        post.setImageBase64(request.Base64image);
        post.setTimestamp(new Date());

        user.getPosts().add(post);
        postRepository.save(post);
        userRepository.save(user);
        return true;
    }
    //like post
}
