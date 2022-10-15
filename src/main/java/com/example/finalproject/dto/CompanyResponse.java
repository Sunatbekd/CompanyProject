package com.example.finalproject.dto;

import lombok.*;


@Getter@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponse {
    private Long id;
    private String companyName;
    private String locatedCountry;

}