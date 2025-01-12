package com.Develoading.backend.repository;

import com.Develoading.backend.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    // Kart ID'sine göre kampanyaları bulma
    List<Campaign> findByCardId(Integer cardId);

    // Şirket ID'sine göre kampanyaları bulma
    List<Campaign> findByCompanyId(Integer companyId);

    // Bitiş tarihine göre sıralanmış kampanyaları bulma
    List<Campaign> findAllByOrderByEndDateAsc();

    // Like sayısını artırma
    @Modifying
    @Transactional
    @Query("UPDATE Campaign c SET c.likeCount = c.likeCount + 1 WHERE c.id = :campaignId")
    void incrementLikeCount(@Param("campaignId") Integer campaignId);

    // Like sayısını azaltma
    @Modifying
    @Transactional
    @Query("UPDATE Campaign c SET c.likeCount = c.likeCount - 1 WHERE c.id = :campaignId AND c.likeCount > 0")
    void decrementLikeCount(@Param("campaignId") Integer campaignId);
}
