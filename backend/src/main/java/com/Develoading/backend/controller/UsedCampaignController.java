package com.Develoading.backend.controller;

import com.Develoading.backend.model.UsedCampaign;
import com.Develoading.backend.service.UsedCampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/used-campaigns")
public class UsedCampaignController {

    @Autowired
    private UsedCampaignService usedCampaignService;

    @PostMapping("/use")
    public ResponseEntity<String> markCampaignAsUsed(@RequestParam Integer userId, @RequestParam Integer campaignId) {
        if (userId == null || campaignId == null) {
            return ResponseEntity.badRequest().body("User ID and Campaign ID cannot be null.");
        }

        UsedCampaign usedCampaign = new UsedCampaign();
        usedCampaign.setUserId(userId);
        usedCampaign.setCampaignId(campaignId);
        usedCampaign.setDbCreatedAt(LocalDateTime.now());
        usedCampaign.setDbStatusId(1); // Varsayılan status ID

        try {
            UsedCampaign savedUsedCampaign = usedCampaignService.saveUsedCampaign(usedCampaign);
            return ResponseEntity.ok("Campaign marked as used successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while saving used campaign: " + e.getMessage());
        }
    }


    // Kullanıcıya ait kullanılan kampanyaları getirme
    @GetMapping("/{userId}")
    public ResponseEntity<List<UsedCampaign>> getUsedCampaignsByUser(@PathVariable Integer userId) {
        List<UsedCampaign> usedCampaigns = usedCampaignService.getUsedCampaignsByUserId(userId);
        if (usedCampaigns.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(usedCampaigns);
    }

    // Belirli bir kampanyayı kullanan kullanıcıları getirme
    @GetMapping("/campaign/{campaignId}")
    public ResponseEntity<List<Integer>> getUsersByCampaign(@PathVariable Integer campaignId) {
        List<UsedCampaign> usedCampaigns = usedCampaignService.getUsedCampaignsByCampaignId(campaignId);
        List<Integer> userIds = usedCampaigns.stream()
                .map(UsedCampaign::getUserId)
                .toList();
        return ResponseEntity.ok(userIds);
    }

    // Kullanıcı ve kampanya ilişkisini silme
    @DeleteMapping("/remove/{userId}/{campaignId}")
    public ResponseEntity<String> removeUsedCampaign(@PathVariable Integer userId, @PathVariable Integer campaignId) {
        try {
            usedCampaignService.removeUsedCampaign(userId, campaignId);
            return ResponseEntity.ok("Used campaign successfully removed.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}