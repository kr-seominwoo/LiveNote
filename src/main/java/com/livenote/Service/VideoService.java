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
import java.util.StringTokenizer;

@Service
public class VideoService {
    final String M3U8 = ".m3u8";
    final String PARSE_TIME = "8";

    public int saveVideo(MultipartFile file, String title) {
        Video video = new Video();


        return 0;
    }

    public void makeHLS(Long id, String filename) throws IOException {
        String extension = getExtension(filename);
        String path = id + "\\" + id;
        FFmpeg ffmpeg = new FFmpeg("src/main/resources/ffmpeg/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("src/main/resources/ffmpeg/bin/ffprobe");

        FFmpegBuilder builder = new FFmpegBuilder()
                //.overrideOutputFiles(true) // 오버라이드 여부
                .setInput(path + extension) // 동영상파일
                .addOutput(path + M3U8) // 설정파일?
                .addExtraArgs("-profile:v", "baseline") //
                .addExtraArgs("-level", "3.0") //
                .addExtraArgs("-start_number", "0") //
                .addExtraArgs("-hls_time", PARSE_TIME) //
                .addExtraArgs("-hls_list_size", "0") //
                .addExtraArgs("-f", "hls") //
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder).run();
    }

    public String getExtension(String filename) {
        StringTokenizer st = new StringTokenizer(filename, ".");
        st.nextToken();
        return st.nextToken();
    }
}
