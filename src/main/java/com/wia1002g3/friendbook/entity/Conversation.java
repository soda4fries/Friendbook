package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String conversationName;

    @ManyToMany(mappedBy = "conversations")
    private ArrayList<UserInfo> userInfos;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conversation")
    List<Message> messages = new ArrayList<>();


}
