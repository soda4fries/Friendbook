package com.wia1002g3.friendbook.DTOs;

import com.wia1002g3.friendbook.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class PostDTO implements Comparable<PostDTO> {
    private Integer id;
    private String caption;
    private String imageBase64;
    private ArrayList<String> likeUser;

    private Date timestamp;

    public PostDTO(Integer id, String caption, String imageBase64, ArrayList<User> likes, Date timestamp) {
        this.id = id;
        this.caption = caption;
        this.imageBase64 = imageBase64;
        this.timestamp = timestamp;
        this.likeUser = new ArrayList<>();

        for (User user : likes) {
            this.likeUser.add(user.getUsername());
        }
    }

    @Override
    public int compareTo(PostDTO o) {
        return this.timestamp.compareTo(o.timestamp);
    }

}
