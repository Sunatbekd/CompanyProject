package com.example.finalproject.repository;

import com.example.finalproject.dto.VideoResponse;
import com.example.finalproject.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {


    @Query("select new com.example.finalproject.dto.VideoResponse(v.id," +
            "v.videoName,v.videoLink)from Video v")
    List<VideoResponse> getAllVideos();
}