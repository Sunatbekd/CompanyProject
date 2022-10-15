package com.example.finalproject.service;

import com.example.finalproject.dto.VideoRequest;
import com.example.finalproject.dto.VideoResponse;
import com.example.finalproject.entity.Lesson;
import com.example.finalproject.entity.Task;
import com.example.finalproject.entity.Video;
import com.example.finalproject.exeptions.NotFoundException;
import com.example.finalproject.repository.LessonRepository;
import com.example.finalproject.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;
    private final LessonRepository lessonRepository;



    public VideoResponse createVideo(VideoRequest request){
        Video video = mapToEntity(request);
        return mapToResponse(videoRepository.save(video));
    }

    public VideoResponse getVideoById(Long id){
        Video video = videoRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Video with id = %s not found",id)));
        return mapToResponse(video);
    }

    public VideoResponse deleteVideoById(Long id){
        Video video = videoRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Video with id = %s not found",id)));
        video.setLesson(null);
        videoRepository.delete(video);
        return mapToResponse(video);
    }

    public VideoResponse updateVideoById(Long id,VideoRequest request){
        Video video = videoRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Video with id = %s not found",id)));
        Video video1 = update(video,request);
        return mapToResponse(videoRepository.save(video1));
    }

    public Video update (Video video, VideoRequest request){
        video.setVideoName(request.getVideoName());
        video.setVideoLink(request.getLink());
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(()-> new NotFoundException("Lesson with id = %s not found"));
        video.setLesson(lesson);
        lesson.setVideo(video);
        return video;
    }

    public List<VideoResponse> getAllVideo(){
        return videoRepository.getAllVideos();
    }

    public Video mapToEntity(VideoRequest request){
        Lesson lesson = lessonRepository.findById(request.getLessonId())
                .orElseThrow(()-> new NotFoundException("Lesson with id = %s not found"));
        Video video = new Video();
        video.setVideoName(request.getVideoName());
        video.setVideoLink(request.getLink());
        video.setLesson(lesson);
        return video;
    }

    public VideoResponse mapToResponse(Video video){
        VideoResponse videoResponse = new VideoResponse();
        videoResponse.setId(video.getId());
        videoResponse.setVideoName(video.getVideoName());
        videoResponse.setLink(video.getVideoLink());
        return videoResponse;
    }
}
