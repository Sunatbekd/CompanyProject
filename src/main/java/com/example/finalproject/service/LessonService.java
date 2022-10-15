package com.example.finalproject.service;

import com.example.finalproject.dto.LessonRequest;
import com.example.finalproject.dto.LessonResponse;
import com.example.finalproject.entity.Course;
import com.example.finalproject.entity.Lesson;
import com.example.finalproject.exeptions.NotFoundException;
import com.example.finalproject.repository.CourseRepository;
import com.example.finalproject.repository.LessonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    public LessonService(LessonRepository lessonRepository, CourseRepository courseRepository) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
    }

    public LessonResponse saveLesson(LessonRequest request){
        Lesson lesson = mapToEntity(request);
        return mapToResponse(lessonRepository.save(lesson));
    }

    public LessonResponse getLessonById(Long id){
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(()->new NotFoundException(String.format("Lesson with id %s not found",id)));
        return mapToResponse(lesson);
    }


    public LessonResponse deleteLessonById(Long id){
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(()->new NotFoundException(String.format("Lesson with id %s not found",id)));
        lesson.setCourse(null);
        lessonRepository.delete(lesson);
        return mapToResponse(lesson);
    }

    public LessonResponse updateLessonById(Long id,LessonRequest request){
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(()->new NotFoundException(String.format("Lesson with id %s not found",id)));
        Lesson lesson1 = update(lesson,request);
        return mapToResponse(lesson1);
    }

    public List<LessonResponse> getAllLessons(){
        return lessonRepository.getAllLessons();
    }

    public Lesson update(Lesson lesson,LessonRequest request){
        lesson.setLessonName(request.getLessonName());
        return lessonRepository.save(lesson);
    }

    public Lesson mapToEntity(LessonRequest request){
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(()-> new NotFoundException("Course with id %s not found"));
        return Lesson.builder()
                .course(course)
                .lessonName(request.getLessonName())
                .build();
    }

    public LessonResponse mapToResponse(Lesson lesson){
        return LessonResponse.builder()
                .id(lesson.getId())
                .lessonName(lesson.getLessonName())
                .build();
    }

}
