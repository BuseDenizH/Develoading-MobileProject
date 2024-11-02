package com.Develoading.backend.repository;
import com.Develoading.backend.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    // Ekstra sorgular ekleyebilirsin
}
