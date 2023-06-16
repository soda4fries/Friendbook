package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.Data;

@Entity
@Data
@EnableJpaRepositories
public class GroupPost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User poster;

    private String caption;
    private String ImageBase64;
    
}
