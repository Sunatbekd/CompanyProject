package com.example.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter

public class InstructorResponseView {
    private List<InstructorResponse>instructorResponse;
    private int currentPage;
    private int totalPage;
}
