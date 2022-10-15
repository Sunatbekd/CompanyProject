package com.example.finalproject.repository;

import com.example.finalproject.dto.CompanyResponse;
import com.example.finalproject.entity.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("select c from Company c where upper(c.companyName) like concat('%',:text,'%')")
    List<Company> searchByCompanyName(@Param("text")String text, Pageable pageable);
}