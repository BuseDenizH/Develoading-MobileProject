package com.Develoading.backend.controller;

import com.Develoading.backend.model.Card;
import com.Develoading.backend.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    // Tüm kartları getir
    @GetMapping
    public List<Card> getAllCards() {
        return cardService.getAllCards();
    }

    // İsme göre arama
    @GetMapping("/search")
    public List<Card> searchCard(@RequestParam("name") String name) {
        return cardService.searchCard(name);
    }

    // Kartı ID'ye göre getir
    @GetMapping("/{id}")
    public ResponseEntity<Card> getCardById(@PathVariable Long id) {
        Card card = cardService.getCardById(id);
        if (card != null) {
            return ResponseEntity.ok(card);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Yeni kart ekle
    @PostMapping
    public Card createCard(@RequestBody Card card) {
        return cardService.saveCard(card);
    }

    // Kartı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
