package com.wia1002g3.friendbook.repository;

import com.wia1002g3.friendbook.entity.FriendshipGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendshipGraphRepository extends JpaRepository<FriendshipGraph, Integer> {
    Optional<FriendshipGraph> findByGraphName(String graphName);

}
