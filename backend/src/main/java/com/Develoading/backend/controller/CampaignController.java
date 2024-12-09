package com.Develoading.backend.controller;

import com.Develoading.backend.model.Campaign;
import com.Develoading.backend.service.CampaignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CampaignController {
    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @GetMapping("/api/campaigns")
    public List<Campaign> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    @GetMapping("/api/campaigns/{id}")
    public Campaign getCampaignById(@PathVariable Integer id) {
        return campaignService.getCampaignById(id);
    }
}
