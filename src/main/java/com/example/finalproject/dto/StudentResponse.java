package com.example.finalproject.dto;

import com.example.finalproject.enums.StudyFormat;
import lombok.*;

import java.util.List;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private StudyFormat studyFormat;
}
