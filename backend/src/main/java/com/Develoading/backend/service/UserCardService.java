package com.Develoading.backend.service;

import com.Develoading.backend.model.UserCard;
import com.Develoading.backend.repository.UserCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserCardService {

    @Autowired
    private UserCardRepository userCardRepository;

    // Kullanıcıya kart ekleme
    public UserCard addCardToUser(Integer userId, Long cardId) {
        // Kartın mevcut olup olmadığını kontrol et
        Optional<UserCard> existingUserCard = userCardRepository.findByUserIdAndCardId(userId, cardId);
        if (existingUserCard.isPresent()) {
            throw new RuntimeException("This user already has this card.");
        }

        // Yeni UserCard oluştur
        UserCard userCard = new UserCard();
        userCard.setUserId(userId);
        userCard.setCardId(cardId);
        userCard.setDbCreatedAt(LocalDateTime.now());
        userCard.setDbStatusId(1); // aktif durumda

        return userCardRepository.save(userCard);
    }

    public void removeCardFromUser(Integer userId, Long cardId) {
        // Kullanıcı ve kart ilişkisini kontrol et
        Optional<UserCard> userCard = userCardRepository.findByUserIdAndCardId(userId, cardId);
        if (userCard.isEmpty()) {
            throw new RuntimeException("This card does not exist for the user.");
        }

        // Kartı kaldır
        userCardRepository.delete(userCard.get());
    }



    // Kullanıcının kartlarını getirme
    public List<UserCard> getUserCards(Integer userId) {
        return userCardRepository.findByUserId(userId);
    }

    // Belirli bir kartı kullanıcıya ait sorgulama
    public Optional<UserCard> getUserCardById(Integer userId, Long cardId) {
        return userCardRepository.findByUserIdAndCardId(userId, cardId);
    }

    public UserCard saveUserCard(UserCard userCard) {
        return userCardRepository.save(userCard);
    }
}
