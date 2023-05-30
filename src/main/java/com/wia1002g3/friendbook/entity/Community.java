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
    private Long id;
    private String communityName;
    private String communityInfo;

    // Other community properties

    @ManyToMany
    @JoinTable(name = "community_userinfo",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "userinfo_id"))
    private List<UserInfo> userInfos;

    @ManyToMany
    @JoinTable(name = "community_post",
            joinColumns = @JoinColumn(name = "community_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private ArrayList<Post> posts;

}
