package com.example.finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyResponseView {

    private List<CompanyResponse > companyResponses;
    private int currentPage;
    private int totalPage;
}
