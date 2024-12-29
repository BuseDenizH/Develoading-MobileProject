package com.Develoading.backend.controller;

import com.Develoading.backend.model.Card;
import com.Develoading.backend.model.Company;
import com.Develoading.backend.service.CardService;
import com.Develoading.backend.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LookupController {

    @Autowired
    private CardService cardService;

    @Autowired
    private CompanyService companyService;

    @GetMapping("/card-name/{cardId}")
    public ResponseEntity<String> getCardName(@PathVariable Long cardId) {
        Card card = cardService.getCardById(cardId);
        if (card != null) {
            return ResponseEntity.ok(card.getName());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/company-name/{companyId}")
    public ResponseEntity<String> getCompanyName(@PathVariable int companyId) {
        Company company = companyService.getCompanyById(companyId);
        if (company != null) {
            return ResponseEntity.ok(company.getName());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}