package com.example.finalproject.service;

import com.example.finalproject.dto.CompanyRequest;
import com.example.finalproject.dto.CompanyResponse;
import com.example.finalproject.dto.CompanyResponseView;
import com.example.finalproject.entity.Company;
import com.example.finalproject.exeptions.NotFoundException;
import com.example.finalproject.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyResponse createCompany(CompanyRequest request) {
        Company company = mapToEntity(request);
        return mapToResponse(companyRepository.save(company));
    }

    public CompanyResponse getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Company with id = %s not found", id)));
        return mapToResponse(company);
    }

    public CompanyResponse deleteCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Company with id = %s not found", id)));
        companyRepository.delete(company);
        return mapToResponse(company);
    }

    public CompanyResponse updateCompanyById(Long id, CompanyRequest request) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Company with id = %s not found", id)));
        Company company1 = updateCompany(company, request);
        return mapToResponse(company1);
    }

    public Company updateCompany(Company company, CompanyRequest request) {
        company.setCompanyName(request.getCompanyName());
        company.setLocatedCountry(request.getLocatedCountry());
        company.setCreated(LocalDateTime.now());
        return companyRepository.save(company);
    }


    public CompanyResponseView getAllCompanyPagination(String text, int page, int size) {
        CompanyResponseView companyResponseView = new CompanyResponseView();
        Pageable pageable = PageRequest.of(page - 1, size);
        companyResponseView.setCompanyResponses(getCompany(search(text, pageable)));
        companyResponseView.setCurrentPage(pageable.getPageNumber() + 1);
        companyResponseView.setTotalPage(companyRepository.findAll(pageable).getTotalPages());
        return companyResponseView;
    }

    private List<CompanyResponse> getCompany(List<Company> companies) {
        List<CompanyResponse> companyResponses = new ArrayList<>();
        for (Company company : companies) {
            companyResponses.add(mapToResponse(company));
        }
        return companyResponses;
    }

    private List<Company> search(String name, Pageable pageable) {
        String text = name == null ? "" : name;
        return companyRepository.searchByCompanyName(text, pageable);
    }


    public Company mapToEntity(CompanyRequest request) {
        return Company.builder()
                .companyName(request.getCompanyName())
                .locatedCountry(request.getLocatedCountry())
                .created(LocalDateTime.now())
                .build();
    }

    public CompanyResponse mapToResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .locatedCountry(company.getLocatedCountry())
                .build();
    }
}
