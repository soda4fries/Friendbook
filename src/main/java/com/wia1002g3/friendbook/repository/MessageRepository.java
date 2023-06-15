package com.wia1002g3.friendbook.repository;

import com.wia1002g3.friendbook.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Integer> {
}
