package com.mushr00m.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@RequestMapping("/bitter")
@Controller
public class BitterController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/setDemoHttpSession")
    public void setDemoHttpSession(){
        String api = "http://localhost:8020/chat/toChatRoom";

    }
}
