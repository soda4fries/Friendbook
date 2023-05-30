package com.wia1002g3.friendbook.entity;


import jakarta.persistence.*;
import org.springframework.data.relational.core.sql.In;

@Entity
public class UserBio {
    @Id
            @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    @OneToOne(mappedBy = "userBio")
    private UserInfo userInfo;
}
