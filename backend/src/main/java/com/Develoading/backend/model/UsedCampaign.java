package com.Develoading.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "used_campaign")
public class UsedCampaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "campaign_id")
    private Integer campaignId;

    @Column(name = "db_created_at")
    private LocalDateTime dbCreatedAt;

    @Column(name = "db_status_id")
    private Integer dbStatusId;

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public LocalDateTime getDbCreatedAt() {
        return dbCreatedAt;
    }

    public void setDbCreatedAt(LocalDateTime dbCreatedAt) {
        this.dbCreatedAt = dbCreatedAt;
    }

    public Integer getDbStatusId() {
        return dbStatusId;
    }

    public void setDbStatusId(Integer dbStatusId) {
        this.dbStatusId = dbStatusId;
    }
}
