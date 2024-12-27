package com.Develoading.backend.service;

import com.Develoading.backend.model.LikedCampaign;
import com.Develoading.backend.repository.LikedCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikedCampaignService {

    private final LikedCampaignRepository likedCampaignRepository;

    @Autowired
    public LikedCampaignService(LikedCampaignRepository likedCampaignRepository) {
        this.likedCampaignRepository = likedCampaignRepository;
    }

    // Yeni liked campaign oluşturur
    public LikedCampaign createLikedCampaign(LikedCampaign likedCampaign) {
        return likedCampaignRepository.save(likedCampaign);
    }

    // Kullanıcı ID ve Campaign ID ile liked campaign'i siler
    public void deleteLikedCampaignByUserIdAndCampaignId(Integer userId, Integer campaignId) {
        likedCampaignRepository.deleteByUserIdAndCampaignId(userId, campaignId);
    }
}
