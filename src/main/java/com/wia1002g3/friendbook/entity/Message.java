package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Message implements Comparable<Message> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    private String message;
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User sender;

    @Override
    public int compareTo(Message o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}
