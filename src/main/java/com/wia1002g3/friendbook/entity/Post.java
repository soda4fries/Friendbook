package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Entity
@Data
@RequiredArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer Id;
    String Body;
    String Image;

    @ManyToOne
    @JoinColumn(name = "UserInfo_id")
    private UserInfo userInfo;

}
