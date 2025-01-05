package com.Develoading.backend.repository;

import com.Develoading.backend.model.UsedCampaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsedCampaignRepository extends JpaRepository<UsedCampaign, Integer> {
    List<UsedCampaign> findByUserId(Integer userId);
    List<UsedCampaign> findByCampaignId(Integer campaignId);
    void deleteByUserIdAndCampaignId(Integer userId, Integer campaignId);
    boolean existsByUserIdAndCampaignId(Integer userId, Integer campaignId);


}