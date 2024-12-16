package com.Develoading.backend.repository;

import com.Develoading.backend.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    // Kategoriye göre filtreleme
    List<Company> findByIndustryId(Integer industryId);

    // İsme göre arama
    List<Company> findByNameContainingIgnoreCase(String name);
}
