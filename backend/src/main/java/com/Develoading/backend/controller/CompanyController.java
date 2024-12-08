package com.Develoading.backend.controller;

import com.Develoading.backend.model.Company;
import com.Develoading.backend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    // Tüm şirketleri getir
    @GetMapping
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    // Kategoriye göre şirketleri getir
    @GetMapping("/filter")
    public List<Company> getCompaniesByCategory(@RequestParam("industryId") Integer industryId) {
        return companyService.getCompaniesByCategory(industryId);
    }

    // İsme göre arama
    @GetMapping("/search")
    public List<Company> searchCompanies(@RequestParam("name") String name) {
        return companyService.searchCompanies(name);
    }

    // Şirket detayını getir
    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable int id) {
        return companyService.getCompanyById(id);
    }

    // Yeni şirket ekle
    @PostMapping("/saveCompany")
    public Company saveCompany(@RequestBody Company company) {
        return companyService.saveCompany(company);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable int id) {
        companyService.deleteCompany(id);
    }

}
