package com.example.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter@Setter
public class CourseRequest {
    private String courseName;
    private String durationInMonth;
    private LocalDate dateOfStart;
    private String image;
    private String description;
    private Long companyId;
}
