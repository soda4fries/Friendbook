package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Entity
@Data
@RequiredArgsConstructor
@Table
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer Id;
    String Body;
    String Image;

    @ManyToOne
    @JoinColumn
    private UserInfo userInfo;

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL)
    private ArrayList<Notification> notifications;


}
