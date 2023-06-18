package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/*
 Allows posts to be send most recent posts first
 */
@Entity
@Data
@RequiredArgsConstructor
public class Post implements Comparable<Post>{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer Id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User poster;

    private String caption;
    private String ImageBase64;

    @OneToMany
    @JoinTable(
            name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likes;


    private Date timestamp;

    @Override
    public int compareTo(Post o) {
        return this.timestamp.compareTo(o.getTimestamp());
    }

}
