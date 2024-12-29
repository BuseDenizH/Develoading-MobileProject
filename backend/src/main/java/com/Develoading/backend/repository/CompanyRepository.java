package com.Develoading.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Develoading.backend.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    // Kategoriye göre filtreleme
    List<Company> findByIndustryId(Integer industryId);

    // İsme göre arama
    List<Company> findByNameContainingIgnoreCase(String name);
}
