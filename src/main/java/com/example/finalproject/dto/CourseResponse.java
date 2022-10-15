package com.example.finalproject.dto;

import lombok.*;

import java.time.LocalDate;

@Getter@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CourseResponse {
    private Long id;
    private String courseName;
    private String durationInMonth;
    private LocalDate dateOfStart;
    private String image;
    private String description;
}
