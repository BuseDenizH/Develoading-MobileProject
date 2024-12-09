package com.Develoading.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user") // Veri tabanındaki tablo adı "user" ise bunu belirtir
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255) // "mail" sütunu için kısıtlama
    private String mail;

    @Column(nullable = false, length = 255) // "password" sütunu için kısıtlama
    private String password;

    @Column(name = "db_created_at") // Veri tabanındaki sütun adını eşleştirir
    private LocalDateTime dbCreatedAt;

    @Column(name = "db_status_id", length = 45) // "db_status_id" için sütun adı eşleştirme
    private String dbStatusId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDbCreatedAt() {
        return dbCreatedAt;
    }

    public void setDbCreatedAt(LocalDateTime dbCreatedAt) {
        this.dbCreatedAt = dbCreatedAt;
    }

    public String getDbStatusId() {
        return dbStatusId;
    }

    public void setDbStatusId(String dbStatusId) {
        this.dbStatusId = dbStatusId;
    }
}
