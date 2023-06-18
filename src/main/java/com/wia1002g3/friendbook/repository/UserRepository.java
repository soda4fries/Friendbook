package com.wia1002g3.friendbook.repository;

import com.wia1002g3.friendbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Integer id);

    ArrayList<User> findAllByUsernameContainingOrFirstNameContainingOrLastNameContaining(String username, String firstName, String lastName);

    boolean existsByUsername(String username);

    @Query(value = "SELECT * FROM user_table WHERE content ILIKE %:keyword%", nativeQuery = true)
    ArrayList<User> searchByBio(String keyword);





    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

}
