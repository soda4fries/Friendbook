package com.wia1002g3.friendbook.DTOs;

import lombok.Data;

@Data
public class CreateGroupRequest {
    String groupName;
    Integer[] members;
}
