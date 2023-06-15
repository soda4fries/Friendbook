package com.wia1002g3.friendbook.entity;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@EnableJpaRepositories
public class GroupPost {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer id;

    private String caption;
    private String ImageBase64;
    
}
