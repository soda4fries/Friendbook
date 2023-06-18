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

/*
   -Uses Template Design pattern to delegate security configured on UserDetails to User class
   -User Builder creation pattern to create, User is Created in services/UserServices and Modified in mapping/UserProfile service
   -Some fields are stored as List as requirement for persistance, but as List is an abstract interface the operation
   are done by the suitable concrete Data structure
   -Additionally, the code makes use of Lombok annotations such as
   @Data, @NoArgsConstructor, and @AllArgsConstructor to reduce boilerplate code and provide commonly used functionality
 */

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


    /* The other User are mapped as Many to Many and stored as tuples in DB, so this creates a Linked-List Graph
       User1 - User2          //User 2 friend
       User2 - User1, User3   //User 1,2 friend
       User3 - User2          //User 2 friend
       Reason for choosing this Data structure
       1) adding or removing edges between vertices can be done in constant time (O(1)) on average
       2) Efficient iteration: Traverse the adjacency list of a vertex with O(deg(v)) time complexity,
          where deg(v) is the degree of the vertex, so finding friendlist is O(deg(v)), this allows bfs to be fast.
          Dfs is implemented in the service/FriendService
       3) Space efficiency for sparse graphs: Linked lists allocate memory only
          for existing edges, saving space for non-existent edges. As in social media most users are not friend with
          ever other user, alot of space is saved.

    */
    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends;

    /* The security config  allows only user with role Admin to access the /Admin/ mapping
       It is a Enum type as only two values are possible
     */
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


    /*
    The users action history is stored here as IDs of the viewed post,
    It is stored as List for persistance requirement, but initialized as LinkedList when user is build
    @Transient annotation ensures it is not saved to Database and when user invokes sign out it is flushed
    User user = User.builder()  //Code from services/userservices
                .username(request.getUsername())
                .....
                .viewedPost(new LinkedList<>())
                ....
                .build();
     */
    @Transient
    private List<Integer> viewedPost;

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
    private String Birthday;
    private String address;
    private Boolean gender;

    //Used for Full text Search
    private String allInfo;

    //Used for Full text Search
    @Column(name = "content")
    private String bio;

    //Storing as List as hobbies are list
    @ElementCollection
    @CollectionTable(name = "Hobbies", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "Hobbies")
    private List<String> hobbies;


    //Storing as List here due to Database limitation, but operations are performed as stack then
    //converted to list in controllers as user Experiance is added on top of one another similar to viewed profile
    @ElementCollection
    @CollectionTable(name = "job_experiences", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "experience")
    private List<String> jobExperiences;

    private String relationStatus;

}
