package com.Develoading.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "industry")
public class Industry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 150)
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
