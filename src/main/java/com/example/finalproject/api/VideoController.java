package com.example.finalproject.api;

import com.example.finalproject.dto.VideoRequest;
import com.example.finalproject.dto.VideoResponse;
import com.example.finalproject.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video")
@PreAuthorize("hasAuthority('ADMIN')")
public class VideoController {

    private final VideoService videoService;
    @PostMapping
    public VideoResponse addVideo(@RequestBody VideoRequest request){
        return videoService.createVideo(request);
    }

    @GetMapping("{id}")
    public VideoResponse getVideoById(@PathVariable Long id){
        return videoService.getVideoById(id);
    }

    @DeleteMapping("{id}")
    public VideoResponse deleteVideoById(@PathVariable Long id ){
        return videoService.deleteVideoById(id);
    }

    @PutMapping("{id}")
    public VideoResponse updateVideoById(@PathVariable Long id,@RequestBody VideoRequest request){
        return videoService.updateVideoById(id,request);
    }
    @GetMapping
    public List<VideoResponse> getAll(){
        return videoService.getAllVideo();
    }
}
