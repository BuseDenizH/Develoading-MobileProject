package com.Develoading.backend.service;

import com.Develoading.backend.model.Company;
import com.Develoading.backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return companyRepository.save(company);
    }

    public void deleteCompany(int id) {
        // Öncelikle şirketin var olup olmadığını kontrol edin
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
        } else {
            throw new RuntimeException("Company with id " + id + " not found.");
        }
    }
}
