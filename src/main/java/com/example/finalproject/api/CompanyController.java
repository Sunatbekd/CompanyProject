package com.example.finalproject.api;

import com.example.finalproject.dto.CompanyRequest;
import com.example.finalproject.dto.CompanyResponse;
import com.example.finalproject.dto.CompanyResponseView;
import com.example.finalproject.service.CompanyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
@PreAuthorize("hasAuthority('ADMIN')")
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public CompanyResponse createCompany(@RequestBody CompanyRequest request){
        System.out.println("working");
        return companyService.createCompany(request);
    }

    @GetMapping("{id}")
    public CompanyResponse getCompanyById(@PathVariable Long id){
        return companyService.getCompanyById(id);
    }

    @DeleteMapping("{id}")
    public CompanyResponse deleteCompanyById(@PathVariable Long id){
        return companyService.deleteCompanyById(id);
    }

    @PutMapping("{id}")
    public CompanyResponse updateCompanyById(@PathVariable Long id, @RequestBody CompanyRequest request){
        return companyService.updateCompanyById(id,request);
    }

    @GetMapping()
    public CompanyResponseView getAllPagination(@RequestParam(name = "text",required = false) String text,
                                                @RequestParam int page,
                                                @RequestParam int size){
        return companyService.getAllCompanyPagination(text,page, size);
    }



}
