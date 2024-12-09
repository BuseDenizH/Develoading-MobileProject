package com.Develoading.backend.repository;

import com.Develoading.backend.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    // İsme göre arama
    List<Card> findByNameContainingIgnoreCase(String name);

    // Kategoriye göre filtreleme (Eğer eklemek isterseniz)
    // List<Card> findByCardTypeId(Integer cardTypeId);
}
