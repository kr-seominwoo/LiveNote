package com.livenote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/index")
    public String getIndex(){
        return "index.html";
    }

    @GetMapping("/video")
    public String getVideoList(){
        return "videoList.html";
    }

    @GetMapping("/video/{id}")
    public String getVideo() {
        return "videoDetail.html";
    }

    @GetMapping("/live")
    public String getLive() {
        return "live.html";
    }
}