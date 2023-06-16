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
    private String email;
    private String phoneNumber;
    private Integer graphID;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends;

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
    private List<Conversation> conversations;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Notification> notifications;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Post> posts;


    //userBio
    private String firstName;
    private String lastName;
    private Integer age;
    private String address;
    private Boolean gender;

    @Column(name = "content")
    private String bio;

    //Storing as List as hobbies are list
    @ElementCollection
    @CollectionTable(name = "Hobbies", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "Hobbies")
    private List<String> hobbies;


    //Storing as List here due to Database limitation, but operations are performed as stack then converted to list in controllers as user Experiance is added on top of one another
    @ElementCollection
    @CollectionTable(name = "job_experiences", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "experience")
    @OrderBy("id DESC") // Optional: Sort experiences in descending order based on ID
    private List<String> jobExperiences;

    private String relationStatus;
}
