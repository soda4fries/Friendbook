package com.wia1002g3.friendbook.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private ArrayList<Integer> viewedPost;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private ArrayList<Post> posts;

    @ManyToMany
    @JoinTable(name = "user_conversations",
            joinColumns = @JoinColumn(name = "user_info_id"),
            inverseJoinColumns = @JoinColumn(name = "conversation_id"))
    private ArrayList<Conversation> conversations;

    @ManyToMany(mappedBy = "userInfos")
    private ArrayList<Community> communities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userInfo")
    private ArrayList<Notification> notifications;
}
