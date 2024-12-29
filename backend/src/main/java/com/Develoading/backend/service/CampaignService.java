package com.Develoading.backend.service;

import com.Develoading.backend.model.Campaign;
import com.Develoading.backend.repository.CampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public List<Campaign> getAllCampaignsss() {
        return campaignRepository.findAll();
    }

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAllByOrderByEndDateAsc();  // Kampanyaları bitiş tarihine göre sıralar
    }


    public Campaign getCampaignById(Integer id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + id));
    }

    public Campaign createCampaign(Campaign campaign) {
        campaign.setDbCreatedAt(java.time.LocalDateTime.now());
        return campaignRepository.save(campaign);
    }

    // Kart ID'sine göre kampanyaları almak için metot
    public List<Campaign> getCampaignsByCardId(Integer cardId) {
        return campaignRepository.findByCardId(cardId);
    }

    @Transactional
    public void incrementLike(Integer campaignId) {
        campaignRepository.incrementLikeCount(campaignId);
    }

    @Transactional
    public void decrementLike(Integer campaignId) {
        campaignRepository.decrementLikeCount(campaignId);
    }
}
