package com.example.finalproject.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstructorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String specialization;
    private List<CourseResponse> courseResponse;
}
