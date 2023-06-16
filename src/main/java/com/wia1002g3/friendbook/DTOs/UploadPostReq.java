package com.wia1002g3.friendbook.DTOs;

import lombok.Data;

//upload post
@Data
public class UploadPostReq {
    String caption;
    String Base64image;
}
