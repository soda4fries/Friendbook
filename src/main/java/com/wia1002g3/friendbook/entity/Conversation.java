package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Data
@Entity
@RequiredArgsConstructor
@Table
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToMany(mappedBy = "conversations")
    private ArrayList<UserInfo> userInfos;


}
