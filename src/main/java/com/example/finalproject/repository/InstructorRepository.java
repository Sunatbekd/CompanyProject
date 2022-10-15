package com.example.finalproject.repository;

import com.example.finalproject.dto.InstructorResponse;
import com.example.finalproject.entity.Course;
import com.example.finalproject.entity.Instructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

//    @Query("select new com.example.finalproject.dto.InstructorResponse(i.id,i.firstName,i.lastName,i.phoneNumber,i.email,i.specialization)" +
//            "from Instructor i")
//    List<InstructorResponse> getAllInstructors();

    @Query("select i from Instructor i where upper(i.lastName) like concat('%',:text,'%') ")
    List<Instructor>searchByInstructorName(@Param("text") String text,Pageable pageable);

}