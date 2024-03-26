package com.livenote.service;

import com.livenote.Video;
import jakarta.servlet.MultipartConfigElement;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class VideoService {
    public int saveVideo(MultipartFile file, String title) {
        Video video = new Video();


        return 0;
    }

    public void makeHls(String filePath) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("src/main/resources/ffmpeg/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("src/main/resources/ffmpeg/bin/ffprobe");

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput("src/main/resources/static/1.mp4")
                .addOutput("src/main/resources/static/1.m3u8")
                .addExtraArgs("-profile:v", "baseline") //
                .addExtraArgs("-level", "3.0") //
                .addExtraArgs("-start_number", "0") //
                .addExtraArgs("-hls_time", "8") //
                .addExtraArgs("-hls_list_size", "0") //
                .addExtraArgs("-f", "hls") //
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
    }



    public
}
