package com.wia1002g3.friendbook.DTOs;

import lombok.Data;

@Data
public class SaveMessageDTO {

    private String message;
    private Integer sender;
    public Integer getSenderId() {
        return sender;
    }
}
