package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;

@Entity
@Data
@Table
public class FriendshipGraph {
    @Id
    private long id;

    @OneToMany(mappedBy = "parentGraph", cascade = CascadeType.ALL)
    private ArrayList<UsersFriends> Relationships;

    public FriendshipGraph(){
        Relationships= new ArrayList<>(0);
    }

    /**
     *
     * @param UserX First person, represented by index of Relationship array
     * @param UserY Second Person, represented by index of Relationship array
     * @return if adding friend was successful
     */
    public boolean addFriend(int UserX, int UserY) {
        if(UserX> Relationships.size()-1 || UserY> Relationships.size()-1)
            return false;
        Relationships.get(UserX).add(UserY);
        //Justification
        Relationships.get(UserX).trimToSize();
        Relationships.get(UserY).add(UserX);
        //Justification
        Relationships.get(UserY).trimToSize();
        return true;
    }

    /**
     *
     * @return adds a new User and returns his id
     */
    public Integer addUser() {
        Relationships.add(new UsersFriends());
        return Relationships.size()-1;
    }

    public Iterator<Integer> showFriends(Integer UserID) {
        return Relationships.get(UserID).iterator();
    }
}
