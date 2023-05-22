package com.wia1002g3.friendbook.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {
    private String firstName;
    private String lastName;
    private Integer age;
    private String address; //research geospatial tools

    private String phoneNumber;

    private String Gender;

    private String Bio;

    private String Hobbies;

    private String RelationStatus;

    private Object partner;

    //testing





}
