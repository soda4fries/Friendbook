package com.wia1002g3.friendbook.repository;

import com.wia1002g3.friendbook.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfosRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> getUserInfoById(Integer id);
}
