package com.wia1002g3.friendbook.DTOs;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO implements Comparable<MessageDTO> {
    private Integer messageId;
    private String message;
    private Date timestamp;
    private Integer senderUserId;
    private String SenderUsername;

    @Override
    public int compareTo(MessageDTO o) {
        return this.timestamp.compareTo(o.getTimestamp());
    }
}