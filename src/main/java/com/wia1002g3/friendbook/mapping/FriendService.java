package com.wia1002g3.friendbook.mapping;

import com.wia1002g3.friendbook.entity.FriendshipGraph;
import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import com.wia1002g3.friendbook.services.FriendshipGraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class FriendService {
    private final UserRepository userRepository;
    private final FriendshipGraphService friendshipGraphService;


    @GetMapping("/Friends/GetAllFriends/{userid}")
    public ResponseEntity<List<Integer>> getFriends(@PathVariable Integer userid) {
        User user = userRepository.findById(userid).orElseThrow();
        Integer graphID = user.getGraphID();

        FriendshipGraph graph = friendshipGraphService.getSingletonFriendshipGraph();
        ArrayList<Integer> GraphFriends = graph.showFriends(graphID);
        ArrayList<Integer> friends = new ArrayList<>();
        for (Integer graphFriend : GraphFriends) {
             friends.add(userRepository.findByGraphID(graphFriend).orElseThrow().getId());
        }
        return ResponseEntity.ok(friends);
    }

    @GetMapping("/Friends/GetAllMutuals/{userid}")
    public ResponseEntity<ArrayList<ArrayList<Integer>>> getRelations(@PathVariable Integer userid) {
        User user = userRepository.findById(userid).orElseThrow();
        Integer graphID = user.getGraphID();

        FriendshipGraph graph = friendshipGraphService.getSingletonFriendshipGraph();
        ArrayList<ArrayList<Integer>> GraphRelations = graph.bfs(graphID);
        ArrayList<ArrayList<Integer>> RelationHops = new ArrayList<>();
        for(int i = 2; i < GraphRelations.size(); i++) {
            ArrayList<Integer> Relations = new ArrayList<>();
            for(int j = 0; j < GraphRelations.get(i).size(); j++) {
                Relations.add(userRepository.findByGraphID(j).orElseThrow().getId());
            }
            RelationHops.add(Relations);
        }

        return ResponseEntity.ok(RelationHops);
    }
}
