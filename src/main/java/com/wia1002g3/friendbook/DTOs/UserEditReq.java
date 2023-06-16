package com.wia1002g3.friendbook.DTOs;

import lombok.Data;

import java.util.ArrayList;

@Data
public class UserEditReq {
    private String firstName;
    private String lastName;
    private String DOB;
    private String address;//research geospatial tools
    private String phoneNumber;
    private Boolean gender;
    private String bio;
    private String hobbies;
    private String relationStatus;

    public ArrayList<String> getHobbiesAsArrayList() {
        String[] hobbyArr = hobbies.split(",");
        ArrayList<String> hobbiesVector = new ArrayList<>();
        for(int i = 0; i < hobbyArr.length; i++) {
            hobbiesVector.add(hobbyArr[i]);
        }
        return hobbiesVector;
    }

}
