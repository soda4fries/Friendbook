package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;

@Entity
@Data
@RequiredArgsConstructor
@Table
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    private String Message;
    private String Type;
}
