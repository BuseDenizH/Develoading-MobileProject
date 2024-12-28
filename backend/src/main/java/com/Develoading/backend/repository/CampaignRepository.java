package com.Develoading.backend.repository;

import com.Develoading.backend.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    // Kart ID'sine göre kampanyaları bulma
    List<Campaign> findByCardId(Integer cardId);

    // Bitiş tarihine göre sıralanmış kampanyaları bulma
    List<Campaign> findAllByOrderByEndDateAsc();
}
