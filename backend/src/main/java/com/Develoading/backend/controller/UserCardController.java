package com.Develoading.backend.controller;

import com.Develoading.backend.model.UserCard;
import com.Develoading.backend.model.Campaign;
import com.Develoading.backend.service.CampaignService;
import com.Develoading.backend.service.UserCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.stream.Collectors;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-cards")
public class UserCardController {

    @Autowired
    private UserCardService userCardService;


    @Autowired
    private CampaignService campaignService;

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

    // Kartlara ait kullanıcıları getirme
    @GetMapping("/cards/{cardId}")
    public ResponseEntity<List<Integer>> getUsersByCard(@PathVariable Long cardId) {
        // Belirli bir kart id'sine ait tüm kullanıcıları buluyoruz
        List<UserCard> userCards = userCardService.getUserCardsByCardId(cardId);

        // Kullanıcı ID'lerini almak için bir liste oluşturuyoruz
        List<Integer> userIds = userCards.stream()
                .map(UserCard::getUserId)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userIds);
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




    @GetMapping("/{userId}/campaigns")
    public ResponseEntity<List<Campaign>> getUserCampaigns(@PathVariable Integer userId) {
        // Kullanıcıya ait kartları alın
        List<UserCard> userCards = userCardService.getUserCards(userId);

        if (userCards.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Kampanyaları toplamak için bir liste oluşturun
        List<Campaign> allCampaigns = new ArrayList<>();

        // Her kart için kampanyaları alın
        for (UserCard userCard : userCards) {
            Long cardId = userCard.getCardId(); // Kart ID'sini alın
            List<Campaign> campaigns = campaignService.getCampaignsByCardId(cardId.intValue()); // Kampanyaları çekin
            allCampaigns.addAll(campaigns); // Kampanyaları listeye ekleyin
        }

        return ResponseEntity.ok(allCampaigns); // Kampanyaları döndürün
    }


}
