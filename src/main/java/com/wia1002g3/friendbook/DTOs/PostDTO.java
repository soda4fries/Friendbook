package com.wia1002g3.friendbook.DTOs;

import com.wia1002g3.friendbook.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PostDTO implements Comparable<PostDTO> {
    private String posterUserName;
    private Integer id;
    private String caption;
        private String imageBase64;
    private List<String> likeUser;

    private Date timestamp;

    public PostDTO(String PosterUserid,Integer id, String caption, String imageBase64, List<User> likes, Date timestamp) {
        this.posterUserName = PosterUserid;
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
        return -1* this.timestamp.compareTo(o.timestamp);
    }

}
