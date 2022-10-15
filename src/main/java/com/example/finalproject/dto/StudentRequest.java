package com.example.finalproject.dto;

import com.example.finalproject.enums.StudyFormat;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class StudentRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private StudyFormat studyFormat;
    private Long companyId;
    private Long courseId;


}
