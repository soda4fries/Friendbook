package com.wia1002g3.friendbook.services;

import com.wia1002g3.friendbook.entity.User;
import com.wia1002g3.friendbook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

@RequiredArgsConstructor
@Service
public class FriendsService {
    private final UserRepository userRepository;

    private ArrayList<User> getFriends(Integer userId) {
        return (ArrayList<User>) userRepository.findById(userId).orElseThrow().getFriends();
    }

    /*
    Performs dfs and finds stores the friend in hops as Arrays of Array of UserIDs
    if user1 -> friend -> friends friend
    then hops(0) contains user1
         hops(1) contains friend
         hops(3) contains friends friend
     */

    public ArrayList<ArrayList<Integer>> bfs(Integer userID) {
        User user = userRepository.findById(userID).orElseThrow();

        ArrayList<ArrayList<Integer>> hops = new ArrayList<>();
        MyHashSet<Integer> visited = new MyHashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(userID);
        visited.add(userID);

        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> currentHop = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
                int next_user = queue.poll();
                currentHop.add(next_user);
                User next_user_obj = userRepository.findById(next_user).orElseThrow();
                for (User person : next_user_obj.getFriends()) {
                    int person_id = person.getId();
                    if (!visited.contains(person_id)) {
                        visited.add(person_id);
                        queue.add(person_id);
                    }
                }
            }
            hops.add(currentHop);
        }
        return hops;
    }

}
