package com.Develoading.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail", length = 255)
    private String mail;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "surname", length = 255)
    private String surname;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "db_created_at")
    private LocalDateTime dbCreatedAt;

    @Column(name = "db_status_id", length = 45)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
