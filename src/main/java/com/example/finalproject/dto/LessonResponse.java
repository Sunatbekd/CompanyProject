package com.example.finalproject.dto;

import lombok.*;

import java.time.LocalDate;

@Getter@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonResponse {
    private Long id;
    private String lessonName;
}
