package com.Develoading.backend.service;

import com.Develoading.backend.model.UsedCampaign;
import com.Develoading.backend.repository.UsedCampaignRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.List;

@Service
public class UsedCampaignService {

    @Autowired
    private UsedCampaignRepository usedCampaignRepository;

    public UsedCampaign saveUsedCampaign(UsedCampaign usedCampaign) {
        return usedCampaignRepository.save(usedCampaign);
    }

    public List<UsedCampaign> getUsedCampaignsByUserId(Integer userId) {
        return usedCampaignRepository.findByUserId(userId);
    }

    public List<UsedCampaign> getUsedCampaignsByCampaignId(Integer campaignId) {
        return usedCampaignRepository.findByCampaignId(campaignId);
    }


    @Transactional  // Bu annotation, işlemin bir transaction içinde yapılmasını sağlar.
    public void removeUsedCampaign(Integer userId, Integer campaignId) {
        usedCampaignRepository.deleteByUserIdAndCampaignId(userId, campaignId);
    }

    public boolean isCampaignUsedByUser(Integer userId, Integer campaignId) {
        // Kullanıcı ve kampanya kombinasyonuyla UsedCampaign var mı kontrol ediyoruz
        return usedCampaignRepository.existsByUserIdAndCampaignId(userId, campaignId);
    }


    @Transactional
    public boolean toggleCampaignUsage(Integer userId, Integer campaignId) {
        boolean isUsed = isCampaignUsedByUser(userId, campaignId);

        if (isUsed) {
            // Kampanya kullanılmışsa, kullanımı kaldır
            removeUsedCampaign(userId, campaignId);
            return false;
        } else {
            // Kampanya kullanılmamışsa, yeni kullanım ekle
            UsedCampaign usedCampaign = new UsedCampaign();
            usedCampaign.setUserId(userId);
            usedCampaign.setCampaignId(campaignId);
            usedCampaign.setDbCreatedAt(LocalDateTime.now());
            usedCampaign.setDbStatusId(1);
            saveUsedCampaign(usedCampaign);
            return true;
        }
    }
}