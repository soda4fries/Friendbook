package com.wia1002g3.friendbook.repository;

import com.wia1002g3.friendbook.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    @Override
    Optional<Conversation> findById(Integer integer);


}
