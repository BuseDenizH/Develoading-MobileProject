package com.Develoading.backend.controller;

import com.Develoading.backend.model.Campaign;
import com.Develoading.backend.service.CampaignService;
import com.Develoading.backend.model.UsedCampaign;
import com.Develoading.backend.service.UsedCampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/used-campaigns")
public class UsedCampaignController {

    @Autowired
    private UsedCampaignService usedCampaignService;

    @Autowired
    private CampaignService campaignService;

    @PostMapping("/use")
    public ResponseEntity<String> markCampaignAsUsed(@RequestParam Integer userId, @RequestParam Integer campaignId) {
        if (userId == null || campaignId == null) {
            return ResponseEntity.badRequest().body("User ID and Campaign ID cannot be null.");
        }

        // Kullanıcının zaten bu kampanyayı kullanıp kullanmadığını kontrol ediyoruz
        boolean isCampaignAlreadyUsed = usedCampaignService.isCampaignUsedByUser(userId, campaignId);

        if (isCampaignAlreadyUsed) {
            // Kampanya zaten kullanılmış, hata mesajı dönüyoruz
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("This campaign has already been used by the user.");
        }

        // Kampanya kullanılmamış, yeni bir UsedCampaign kaydı oluşturuyoruz
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

//kullancıın kullandıgı kampanyanın idsini gönderirir
@GetMapping("/campaigns/{userId}")
public ResponseEntity<List<Integer>> getCampaignIdsByUser(@PathVariable Integer userId) {
    // Kullanıcının kampanyalarını alıyoruz
    List<UsedCampaign> usedCampaigns = usedCampaignService.getUsedCampaignsByUserId(userId);

    // Eğer hiç kampanya yoksa 204 No Content döndürüyoruz
    if (usedCampaigns.isEmpty()) {
        return ResponseEntity.noContent().build();
    }

    // CampaignId'leri almak için dönüşüm yapıyoruz
    List<Integer> campaignIds = usedCampaigns.stream()
            .map(UsedCampaign::getCampaignId)  // Sadece campaignId'yi alıyoruz
            .collect(Collectors.toList());

    // campaignId'leri döndürüyoruz
    return ResponseEntity.ok(campaignIds);
}


    @GetMapping("/campaign-details/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getCampaignDetailsByUser(@PathVariable Integer userId) {
        // Kullanıcının kullandığı kampanyaları alıyoruz
        List<UsedCampaign> usedCampaigns = usedCampaignService.getUsedCampaignsByUserId(userId);

        // Eğer hiç kampanya yoksa 204 No Content döndürüyoruz
        if (usedCampaigns.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Kullanıcının kullandığı kampanya ID'lerini alıyoruz
        List<Integer> campaignIds = usedCampaigns.stream()
                .map(UsedCampaign::getCampaignId)
                .collect(Collectors.toList());

        // Kampanya detaylarını tutmak için bir liste oluşturuyoruz
        List<Map<String, Object>> campaignDetails = new ArrayList<>();

        // Her kampanya ID'si için CampaignService'den kampanya detaylarını alıyoruz
        for (Integer campaignId : campaignIds) {
            // Kampanya zaten kullanıldı mı kontrol ediyoruz
            // Eğer kullanıcı bu kampanyayı daha önce kullanmışsa, bu kampanyayı tekrar almayı engelliyoruz
            if (campaignDetails.stream().anyMatch(c -> c.get("id").equals(campaignId))) {
                continue; // Eğer kampanya zaten listede varsa, geçiyoruz
            }

            try {
                // CampaignController'dan kampanya detayını alıyoruz
                Campaign campaign = campaignService.getCampaignById(campaignId);

                // Kampanya detaylarını map şeklinde ekliyoruz
                Map<String, Object> campaignDetail = new HashMap<>();
                campaignDetail.put("id", campaign.getId());
                campaignDetail.put("title", campaign.getTitle());
                campaignDetail.put("detail", campaign.getDetail());
                campaignDetail.put("url", campaign.getUrl());
                campaignDetail.put("image", campaign.getImage());
                campaignDetail.put("beginDate", campaign.getBeginDate());
                campaignDetail.put("endDate", campaign.getEndDate());

                campaignDetails.add(campaignDetail);
            } catch (Exception e) {
                // Hata durumunda kullanıcıya uygun bir mesaj veriyoruz
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Collections.singletonList(Map.of("error", "Error fetching campaign details for campaignId: " + campaignId)));
            }
        }

        // Kampanya detaylarını döndürüyoruz
        return ResponseEntity.ok(campaignDetails);
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