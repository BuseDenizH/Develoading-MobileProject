package com.Develoading.backend.repository;

import com.Develoading.backend.model.LikedCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface LikedCampaignRepository extends JpaRepository<LikedCampaign, Integer> {
    Optional<LikedCampaign> findByUserIdAndCampaignId(Integer userId, Integer campaignId);

    // Kullanıcı ID ve Campaign ID'ye göre liked campaign'i silmek için method
    void deleteByUserIdAndCampaignId(Integer userId, Integer campaignId);

    List<LikedCampaign> findByUserId(Integer userId);
}
