package com.Develoading.backend.controller;

import com.Develoading.backend.model.Campaign;
import com.Develoading.backend.service.CampaignService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {
    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }//bitiş tarihine göre sıralar

    @GetMapping("/allrandom")
    public List<Campaign> getAllCampaignsss() {
        return campaignService.getAllCampaignsss();
    }//rastgele hepsini sıralar


    @GetMapping("/{id}")
    public Campaign getCampaignById(@PathVariable Integer id) {
        return campaignService.getCampaignById(id);
    }

    @PostMapping
    @CrossOrigin(origins = "")
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        Campaign createdCampaign = campaignService.createCampaign(campaign);
        return new ResponseEntity<>(createdCampaign, HttpStatus.CREATED);
    }

    // Kart ID'sine göre kampanyaları döndüren yeni GET endpoint'i
    @GetMapping("/by-card/{cardId}")
    public ResponseEntity<List<Campaign>> getCampaignsByCardId(@PathVariable Integer cardId) {
        List<Campaign> campaigns = campaignService.getCampaignsByCardId(cardId);
        if (campaigns.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(campaigns, HttpStatus.OK);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeCampaign(@PathVariable Integer id) {
        campaignService.incrementLike(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/unlike")
    public ResponseEntity<Void> unlikeCampaign(@PathVariable Integer id) {
        campaignService.decrementLike(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}