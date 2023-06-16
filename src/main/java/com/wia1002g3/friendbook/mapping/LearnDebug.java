package com.wia1002g3.friendbook.mapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LearnDebug {
    @GetMapping("/test/test")
    public String test(){
        System.out.println("testing here");
        return "tested";
    }
}
