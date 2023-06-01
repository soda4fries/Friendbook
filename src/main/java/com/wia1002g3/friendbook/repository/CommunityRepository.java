package com.wia1002g3.friendbook.repository;

import com.wia1002g3.friendbook.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Integer> {
    @Override
    Optional<Community> findById(Integer integer);
}
