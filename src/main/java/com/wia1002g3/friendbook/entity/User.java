package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_Table")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;


    private String username;
    private String password;
    private Integer graphID;

    @Enumerated(EnumType.STRING)
    private Role role;




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    //UserInfo
    @Transient
    private ArrayList<Integer> viewedPost;



    private ArrayList<Conversation> conversations;

    private ArrayList<Community> communities;

    private ArrayList<Notification> notifications;


    private ArrayList<Post> posts;

    private ArrayList<Community> communities;


    //userBio
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;//research geospatial tools
    private String phoneNumber;
    private String gender;
    private String bio;
    private String hobbies;
    private String relationStatus;
    private Object partner;

}
