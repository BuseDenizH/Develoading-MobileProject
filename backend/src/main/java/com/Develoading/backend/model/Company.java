package com.Develoading.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "industry_id", nullable = false)
    private int industryId;

    @Column(length = 150) //
    private String image;

    @Column(name = "db_created_at", updatable = false)
    private LocalDateTime dbCreatedAt;

    @Column(name = "db_status_id")
    private int dbStatusId;

    @PrePersist
    protected void onCreate() {
        this.dbCreatedAt = LocalDateTime.now();
        this.dbStatusId = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getDbCreatedAt() {
        return dbCreatedAt;
    }

    public int getDbStatusId() {
        return dbStatusId;
    }

    public void setDbStatusId(int dbStatusId) {
        this.dbStatusId = dbStatusId;
    }
}
