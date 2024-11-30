package com.Develoading.backend.repository;

import com.Develoading.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByMail(String email);
}