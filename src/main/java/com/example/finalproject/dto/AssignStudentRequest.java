package com.example.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class AssignStudentRequest {
    private Long courseId;
    private Long studentId;
}
