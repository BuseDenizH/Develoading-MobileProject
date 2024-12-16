package com.Develoading.backend.repository;

import com.Develoading.backend.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    // Buraya gerektiğinde özel sorgular ekleyebilirsiniz.
}
