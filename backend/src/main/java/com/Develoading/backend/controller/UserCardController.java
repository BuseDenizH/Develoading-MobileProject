package com.Develoading.backend.controller;

import com.Develoading.backend.model.UserCard;
import com.Develoading.backend.service.UserCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-cards")
public class UserCardController {

    @Autowired
    private UserCardService userCardService;

    // Kullanıcıya kart ekleme
    @PostMapping("/add")
    public ResponseEntity<UserCard> addCardToUser(@RequestParam Integer userId, @RequestParam Long cardId) {
        UserCard userCard = userCardService.addCardToUser(userId, cardId);
        return ResponseEntity.ok(userCard);
    }

    // Kullanıcıya ait kartları getirme
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserCard>> getUserCards(@PathVariable Integer userId) {
        List<UserCard> userCards = userCardService.getUserCards(userId);
        return ResponseEntity.ok(userCards);
    }

    // Kullanıcıya ait tek bir kartı getirme
    @GetMapping("/{userId}/{cardId}")
    public ResponseEntity<UserCard> getUserCard(@PathVariable Integer userId, @PathVariable Long cardId) {
        Optional<UserCard> userCard = userCardService.getUserCardById(userId, cardId);
        return userCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }



    @PostMapping
    public ResponseEntity<UserCard> addUserCard(@RequestBody UserCard userCard) {
        userCard.setDbCreatedAt(LocalDateTime.now());  // Şu anki tarihi otomatik ekle
        userCard.setDbStatusId(1);  // Varsayılan status ID
        UserCard savedUserCard = userCardService.saveUserCard(userCard);
        return ResponseEntity.ok(savedUserCard);
    }


    @DeleteMapping("/remove/{userId}/{cardId}")
    public ResponseEntity<String> removeCardFromUser(@PathVariable Integer userId, @PathVariable Long cardId) {
        try {
            userCardService.removeCardFromUser(userId, cardId);
            return ResponseEntity.ok("Card successfully removed.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
