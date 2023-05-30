package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;

@Entity
@Data
@RequiredArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    private String Message;
    private String Type;

    @ManyToOne
    @JoinColumn(name = "user_info_id")
    private UserInfo userInfo;
}
