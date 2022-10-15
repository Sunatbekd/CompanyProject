package com.example.finalproject.repository;

import com.example.finalproject.dto.StudentResponse;
import com.example.finalproject.entity.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    @Query("select new com.example.finalproject.dto.StudentResponse(s.id , s.firstName," +
            "s.lastName,s.phoneNumber,s.email,s.studyFormat) from Student s")
    List<StudentResponse> getAllStudents();


    @Query("select s from Student s where upper(s.firstName ) like concat('%',:text, '%')")
    List<Student> searchStudentByFirstName(@Param("text") String text, Pageable pageable);

}