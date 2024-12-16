package com.Develoading.backend.service;

import com.Develoading.backend.model.Campaign;
import com.Develoading.backend.repository.CampaignRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampaignService {
    private final CampaignRepository campaignRepository;

    public CampaignService(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    public Campaign getCampaignById(Integer id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found with ID: " + id));
    }

    public Campaign createCampaign(Campaign campaign) {
        campaign.setDbCreatedAt(java.time.LocalDateTime.now());
        return campaignRepository.save(campaign);
    }
}
