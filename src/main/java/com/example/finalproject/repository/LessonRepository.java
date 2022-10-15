package com.example.finalproject.repository;

import com.example.finalproject.dto.LessonResponse;
import com.example.finalproject.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select new com.example.finalproject.dto.LessonResponse(l.id , l.lessonName )from Lesson l")
    List<LessonResponse> getAllLessons();
}