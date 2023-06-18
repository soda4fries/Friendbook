package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/*
Allows messages to be sorted based on their timestamps
by implementing the compareTo() method and it is used for sending top messages for viewing by paging them
 */
@Entity
@Data
public class Message implements Comparable<Message> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    private String message;
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @Override
    public int compareTo(Message o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}
