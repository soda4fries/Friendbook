package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@Table
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String communityName;
    private String communityInfo;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "community_id")
    private ArrayList<GroupPost> groupPosts;

}
