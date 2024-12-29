package com.Develoading.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Develoading.backend.model.Company;
import com.Develoading.backend.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    // Tüm şirketleri getir
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    // Kategoriye göre filtreleme
    public List<Company> getCompaniesByCategory(Integer industryId) {
        return companyRepository.findByIndustryId(industryId);
    }

    // İsme göre arama
    public List<Company> searchCompanies(String name) {
        return companyRepository.findByNameContainingIgnoreCase(name);
    }

    // Şirket detayını getir
    public Company getCompanyById(int id) {
        Optional<Company> company = companyRepository.findById(id);
        return company.orElse(null);
    }

    // Yeni şirket ekle
    public Company saveCompany(Company company) {
        if (company.getDbStatusId() == 0) {
            company.setDbStatusId(1);
        }
        return companyRepository.save(company);
    }

    public void deleteCompany(int id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
        } else {
            throw new RuntimeException("Company with id " + id + " not found.");
        }
    }
}
