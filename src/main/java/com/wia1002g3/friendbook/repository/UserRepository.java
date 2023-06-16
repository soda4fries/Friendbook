package com.wia1002g3.friendbook.repository;

import com.wia1002g3.friendbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Optional<User> findById(Integer id);

    ArrayList<User> findAllByUsernameContainingOrFirstNameContainingOrLastNameContaining(String username, String firstName, String lastName);

    boolean existsByUsername(String username);

    @Query(value = "SELECT u FROM User u WHERE to_tsvector('english', u.bio) @@ to_tsquery('english', :query)", nativeQuery = true)
    ArrayList<User> searchByBio(@Param("query") String query);

    Optional<User> findByEmailOrPhoneNumber(String email, String phoneNumber);

    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

}
