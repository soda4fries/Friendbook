package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    @ManyToOne
            @JoinColumn(name = "conversation_id")
    Conversation conversation;
}
