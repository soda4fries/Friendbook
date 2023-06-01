package com.wia1002g3.friendbook.services;

import com.wia1002g3.friendbook.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

}
