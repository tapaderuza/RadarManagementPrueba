package com.example.repository;

import com.example.model.Infraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InfractionRepository extends JpaRepository<Infraction, Long> {
    List<Infraction> findByRadarId(Long id);
    List<Infraction> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}

