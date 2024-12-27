package com.Develoading.backend.controller;

import com.Develoading.backend.model.LikedCampaign;
import com.Develoading.backend.repository.LikedCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/likedCampaigns")
public class LikedCampaignController {

    @Autowired
    private LikedCampaignRepository likedCampaignRepository;

    @PostMapping
    public LikedCampaign likeCampaign(@RequestBody LikedCampaign likedCampaign) {
        likedCampaign.setDbCreatedAt(LocalDateTime.now());
        likedCampaign.setDbStatusId(1); // Varsayılan durum ID'si
        return likedCampaignRepository.save(likedCampaign);
    }

    @DeleteMapping("/{userId}/{campaignId}")
    public void unlikeCampaign(@PathVariable Integer userId, @PathVariable Integer campaignId) {
        Optional<LikedCampaign> likedCampaign = likedCampaignRepository.findByUserIdAndCampaignId(userId, campaignId);
        likedCampaign.ifPresent(likedCampaignRepository::delete);
    }

    @GetMapping("/{userId}")
    public List<LikedCampaign> getLikedCampaignsByUser(@PathVariable Integer userId) {
        // Kullanıcının beğendiği kampanyaları döndürüyoruz
        return likedCampaignRepository.findByUserId(userId);
    }
}
