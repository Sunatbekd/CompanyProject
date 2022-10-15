package com.example.finalproject.api;

import com.example.finalproject.dto.LessonRequest;
import com.example.finalproject.dto.LessonResponse;
import com.example.finalproject.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lesson")
@PreAuthorize("hasAuthority('INSTRUCTOR')")
public class LessonController {

    private final LessonService lessonService;

    @PostMapping
    public LessonResponse saveLesson(@RequestBody LessonRequest request){
        return lessonService.saveLesson(request);
    }

    @GetMapping("{id}")
    public LessonResponse getLessonById(@PathVariable Long id){
        return lessonService.getLessonById(id);
    }

    @DeleteMapping("{id}")
    public LessonResponse deleteLessonById(@PathVariable Long id){
        return lessonService.deleteLessonById(id);
    }

    @PutMapping("{id}")
    public LessonResponse updateLessonById(@PathVariable Long id,@RequestBody LessonRequest request){
        return lessonService.updateLessonById(id,request);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('STUDENT','INSTRUCTOR')")
    public List<LessonResponse>getAll(){
        return lessonService.getAllLessons();
    }
}
