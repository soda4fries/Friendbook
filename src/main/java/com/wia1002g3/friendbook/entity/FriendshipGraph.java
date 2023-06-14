package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

@Entity
@Data
public class FriendshipGraph {
    @Id
    private Integer id;

    private String graphName;

    @OneToMany(mappedBy = "parentGraph", cascade = CascadeType.ALL)
    private ArrayList<UsersFriends> relationships;


    public FriendshipGraph(){
        relationships= new ArrayList<>(0);
        this.graphName = graphName;
    }

    /**
     *
     * @param UserX First person, represented by index of Relationship array
     * @param UserY Second Person, represented by index of Relationship array
     * @return if adding friend was successful
     */
    public boolean addFriend(int UserX, int UserY) {
        if(UserX> relationships.size()-1 || UserY> relationships.size()-1)
            return false;
        relationships.get(UserX).add(UserY);
        //Justification
        relationships.get(UserX).trimToSize();
        relationships.get(UserY).add(UserX);
        //Justification
        relationships.get(UserY).trimToSize();
        return true;
    }

    /**
     *
     * @return adds a new User and returns his graphIndex
     */
    public Integer addUser() {
        relationships.add(new UsersFriends());
        return relationships.size()-1;
    }

    public Iterator<Integer> showFriends(Integer UserID) {
        return relationships.get(UserID).iterator();
    }

    /**
     * Performs Breadth-First Search (BFS) starting from the given UserID and stores all vertices visited
     * in each hop in an ArrayList.
     *
     * @param userID The graph index of the user to start the BFS from
     * @return An ArrayList containing all vertices visited in each hop
     * using boolean instead of hashset as number of edge more than vertices
     */
    public ArrayList<ArrayList<Integer>> bfs(int userID) {
        ArrayList<ArrayList<Integer>> hops = new ArrayList<>();
        boolean[] visited = new boolean[relationships.size()];

        Queue<Integer> queue = new LinkedList<>();
        queue.add(userID);
        visited[userID] = true;

        while (!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> currentHop = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
                int user = queue.poll();
                currentHop.add(user);

                for (int friend : relationships.get(user)) {
                    if (!visited[friend]) {
                        visited[friend] = true;
                        queue.add(friend);
                    }
                }
            }
            hops.add(currentHop);
        }

        return hops;
    }
}
