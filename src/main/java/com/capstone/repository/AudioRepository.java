package com.capstone.repository;

import com.capstone.domain.Audio;
import com.capstone.domain.AudioType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AudioRepository extends JpaRepository<Audio, Long> {
    List<Audio> findAllByUserUserId(Long userId);


    @Query("SELECT a FROM Audio a " +
            "WHERE a.user.userId = :userId " +
            "AND a.audioType = :audioType " +
            "ORDER BY a.audioId DESC")
    Optional<Audio> findLatestAudio(@Param("userId") Long userId, @Param("audioType") AudioType audioType);



} 