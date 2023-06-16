package com.wia1002g3.friendbook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

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


    @Transient
    private LinkedList<Integer> viewedPost;

    @ManyToMany
    @JoinTable(
            name = "user_conversation",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "conversation_id")
    )
    private ArrayList<Conversation> conversations;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private ArrayList<Notification> notifications = new ArrayList<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private ArrayList<Post> posts;

    @ManyToMany
    @JoinTable(
            name = "user_community",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "community_id")
    )
    private ArrayList<Community> communities;


    //userBio
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;//research geospatial tools
    private String phoneNumber;
    private Boolean gender;

    @Column(name = "content")
    private String bio;

    //Storing as ArrayList as users hobbies are a list
    @ElementCollection
    @CollectionTable(name = "Hobbies", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "Hobbies")
    private ArrayList<String> hobbies;


    //Storing as stack as user Experiance is added on top of one another
    @ElementCollection
    @CollectionTable(name = "job_experiences", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "experience")
    @OrderBy("id DESC") // Optional: Sort experiences in descending order based on ID
    private Stack<String> jobExperiences = new Stack<>();

    private String relationStatus;
}
