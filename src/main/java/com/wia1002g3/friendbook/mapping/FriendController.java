package com.wia1002g3.friendbook.mapping;

import com.wia1002g3.friendbook.DTOs.UserDTO;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import com.wia1002g3.friendbook.services.FriendsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FriendController {
    private final UserRepository userRepository;
    private final FriendsService friendsService;




    @GetMapping("/Friends/GetFriendsList/{userid}")
    public ResponseEntity<List<Integer>> getFriends(@PathVariable Integer userid) {
        User user = userRepository.findById(userid).orElseThrow();
        List friendIDs = new ArrayList<>();
        for (User friends : user.getFriends()) {
            friendIDs.add(friends.getId());
        }
        return ResponseEntity.ok(friendIDs);
    }

    @GetMapping("/Friends/GetMutualFriends/{userid1}/{userid2}")
    public ResponseEntity<ArrayList<UserDTO>> getMutualFriends(@PathVariable Integer userid1, @PathVariable Integer userid2) {
        User user1 = userRepository.findById(userid1).orElseThrow();
        User user2 = userRepository.findById(userid2).orElseThrow();

        ArrayList<User> User1Friends = (ArrayList<User>) user1.getFriends();
        ArrayList<User> User2Friends = (ArrayList<User>) user2.getFriends();

        ArrayList<User> mutualFriend = new ArrayList<>();

        for (User friend : User1Friends) {
            if (User2Friends.contains(friend)) {
                mutualFriend.add(friend);
            }
        }
        if (mutualFriend.size()==0) return ResponseEntity.notFound().build();

        ArrayList<UserDTO> friends = new ArrayList<>();
        for (User Friend : mutualFriend) {
            friends.add(new UserDTO(Friend.getId(), Friend.getUsername()));
        }
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/Friends/GetAllEnhancedNetwork/{userid}")
    public ResponseEntity<ArrayList<ArrayList<UserDTO>>> getRelations(@PathVariable Integer userid) {
        ArrayList<ArrayList<Integer>> hops = friendsService.bfs(userid);

        ArrayList<ArrayList<UserDTO>> network = new ArrayList<>();
        for (ArrayList<Integer> hop : hops) {
            ArrayList<UserDTO> hopDTO = new ArrayList<>();
            for (Integer personID : hop) {
                User Person = userRepository.findById(personID).orElseThrow();
                hopDTO.add(new UserDTO(Person.getId(), Person.getUsername()));
            }
            network.add(hopDTO);
        }
        return ResponseEntity.ok(network);
    }

    @GetMapping("/Friends/AddFriend/{userid1}/{userid2}")
    public ResponseEntity<Boolean> getRelations(@PathVariable Integer userid1, @PathVariable Integer userid2) {
        User user1 = userRepository.findById(userid1).orElseThrow();
        User user2 = userRepository.findById(userid2).orElseThrow();

        user1.getFriends().add(user2);
        user2.getFriends().add(user1);
        userRepository.save(user1);
        userRepository.save(user2);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
