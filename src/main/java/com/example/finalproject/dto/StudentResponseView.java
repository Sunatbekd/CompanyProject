package com.example.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class StudentResponseView {
    private List<StudentResponse> studentResponse;
    private int currentPage;
    private int totalPage;
}
