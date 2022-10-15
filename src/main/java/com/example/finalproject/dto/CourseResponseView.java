package com.example.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class CourseResponseView {

    private List<CourseResponse> courseResponses;
    private int currentPage;
    private int totalPage;
}
