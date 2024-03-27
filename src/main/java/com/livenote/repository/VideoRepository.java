package com.livenote.repository;

import com.livenote.Video;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class VideoRepository {
    @Autowired
    private EntityManager em;

    public Long save(Video video) {
        em.persist(video);
        Long id = video.getId();
        video.setPath(id + "\\" + id);
        return id;
    }
}
