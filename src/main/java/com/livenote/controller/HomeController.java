package com.livenote.controller;

import com.livenote.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {
    @Autowired
    VideoService videoService;

    @GetMapping("/index")
    public String getIndex(){
        return "index.html";
    }

    @GetMapping("/video")
    public String getVideoList(){
        return "videoList.html";
    }

    @GetMapping("/video/{id}")
    public String getVideo() throws IOException {
        videoService.makeHLS("aaa");
        return "videoDetail.html";
    }

    @GetMapping("/live")
    public String getLive() {
        return "live.html";
    }
}