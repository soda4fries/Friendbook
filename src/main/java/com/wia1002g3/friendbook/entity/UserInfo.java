package com.wia1002g3.friendbook.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @OneToOne(mappedBy = "userInfo")
    private User user;

    @Transient
    private ArrayList<Integer> viewedPost;

}
