package com.Develoading.backend.repository;

import com.Develoading.backend.model.UserCard;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserCardRepository extends JpaRepository<UserCard, Integer> {
    // Kullanıcıya ait kartları bulma
    List<UserCard> findByUserId(Integer userId);

    // Kartın id'siyle kullanıcıya ait kartı bulma
    Optional<UserCard> findByUserIdAndCardId(Integer userId, Long cardId);

    void deleteByUserIdAndCardId(Long userId, Long cardId);
}
