package com.example.finalproject.repository;

import com.example.finalproject.dto.CourseResponse;
import com.example.finalproject.entity.Course;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c where upper(c.courseName) like concat('%',:text, '%') ")
    List<Course> searByCourseName(@Param("text") String text, Pageable pageable);

    @Query("select c from Course c where c.company.id=:id")
    List<Course> findCourseByCompanyId(Long id);

}