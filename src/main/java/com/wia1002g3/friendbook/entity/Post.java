package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer Id;

    private String caption;
    private String ImageBase64;

    @OneToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private ArrayList<User> likes;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Community community;

}
