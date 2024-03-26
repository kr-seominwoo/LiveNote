package com.livenote.controller;

import com.livenote.Video;
import com.livenote.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class VideoController {
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

        return "videoDetail.html";
    }

    @GetMapping("/video/upload")
    public String uploadVideoForm() {
        return "uploadVideoForm.html";
    }

    @PostMapping(path = "/video/upload")
    public String uploadVideo(@RequestParam("file") MultipartFile file, String title) throws IOException {
        if (!file.isEmpty()) {
            videoService.saveVideo(file, title);
            String fullPath = file.getOriginalFilename();
            file.transferTo(new File(fullPath));
        }

        return "redirect:/video";
    }

    @GetMapping("/live")
    public String getLive() {
        return "live.html";
    }
}