package com.livenote.Service;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class VideoService {
    public void makeHLS(String filePath) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("src/main/resources/ffmpeg/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("src/main/resources/ffmpeg/bin/ffprobe");

        FFmpegBuilder builder = new FFmpegBuilder()
                //.overrideOutputFiles(true) // 오버라이드 여부
                .setInput("src/main/resources/static/nothing.mp4") // 동영상파일
                .addOutput("src/main/resources/static/nothing.m3u8") // 설정파일?
                .addExtraArgs("-profile:v", "baseline") //
                .addExtraArgs("-level", "3.0") //
                .addExtraArgs("-start_number", "0") //
                .addExtraArgs("-hls_time", "8") //
                .addExtraArgs("-hls_list_size", "0") //
                .addExtraArgs("-f", "hls") //
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();

        // 이미지 파일 생성
//        FFmpegBuilder builderThumbNail = new FFmpegBuilder()
//                .overrideOutputFiles(true) // 오버라이드 여부
//                .setInput("src/main/resources/static/tiger.mp4") // 동영상파일
//                .addExtraArgs("-ss", "00:00:03") // 썸네일 추출 싲가점
//                .addOutput("src/main/resources/static/nothing.png") // 썸네일 경로
//                .setFrames(1) // 프레임 수
//                .done();
//        FFmpegExecutor executorThumbNail = new FFmpegExecutor(ffmpeg, ffprobe);
//        executorThumbNail.createJob(builderThumbNail).run();
    }
}
