package com.Develoading.backend.service;

import com.Develoading.backend.model.Card;
import com.Develoading.backend.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }





/*
    // Kategoriye göre filtreleme
    public List<Company> getCompaniesByCategory(Integer industryId) {
        return companyRepository.findByIndustryId(industryId);
    }*/

    // İsme göre arama
    public List<Card> searchCard(String name) {
        return cardRepository.findByNameContainingIgnoreCase(name);
    }
}








