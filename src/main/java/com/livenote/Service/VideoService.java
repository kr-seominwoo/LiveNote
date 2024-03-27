package com.livenote.service;

import com.livenote.Video;
import com.livenote.repository.VideoRepository;
import jakarta.servlet.MultipartConfigElement;
import jakarta.transaction.Transactional;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;

@Transactional
@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;
    final String M3U8 = ".m3u8";
    final String PARSE_TIME = "8";

    public int saveVideo(MultipartFile file, String title) throws IOException {
        Video video = new Video();
        video.setTitle(title);
        final Long ID = videoRepository.save(video);
        final String EXTENSION = getExtension(file.getOriginalFilename());
        final String PATH = ID + "\\" + ID;

        Path directoryPath = Paths.get(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\"+ ID);
        String fullPath = directoryPath.toString() + "\\" + ID;
        Files.createDirectories(directoryPath);
        file.transferTo(new File(PATH + EXTENSION));
        makeHLS(fullPath, EXTENSION);
        return 0;
    }

    public void makeHLS(String fullPath, String EXTENSION) throws IOException {
        FFmpeg ffmpeg = new FFmpeg("src/main/resources/ffmpeg/bin/ffmpeg");
        FFprobe ffprobe = new FFprobe("src/main/resources/ffmpeg/bin/ffprobe");

        FFmpegBuilder builder = new FFmpegBuilder()
                //.overrideOutputFiles(true) // 오버라이드 여부
                .setInput(fullPath + EXTENSION) // 동영상파일
                .addOutput(fullPath + M3U8) // 설정파일?
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
        return "." + st.nextToken();
    }
}
