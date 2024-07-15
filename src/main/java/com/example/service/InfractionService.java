package com.example.service;

import com.example.model.Infraction;
import com.example.model.Radar;
import com.example.repository.InfractionRepository;
import com.example.repository.RadarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InfractionService {
    private final InfractionRepository infractionRepository;
    private final RadarRepository radarRepository;

    @Autowired
    public InfractionService(InfractionRepository infractionRepository, RadarRepository radarRepository) {
        this.infractionRepository = infractionRepository;
        this.radarRepository = radarRepository;
    }

    public List<Infraction> getInfractionsByRadar(Long radarId) {
        return infractionRepository.findByRadarId(radarId);
    }

    public List<Infraction> getInfractionsByDateRange(LocalDateTime start, LocalDateTime end) {
        return infractionRepository.findByTimestampBetween(start, end);
    }

    public long countInfractionsByRadar(Long radarId) {
        return infractionRepository.findByRadarId(radarId).size();
    }

    public long countInfractionsByDateRange(LocalDateTime start, LocalDateTime end) {
        return infractionRepository.findByTimestampBetween(start, end).size();
    }

    public Infraction saveInfraction(Infraction infraction) {
        if (infraction.getRadar() == null || infraction.getRadar().getId() == null) {
            throw new IllegalArgumentException("Radar must not be null");
        }

        Radar radar = radarRepository.findById(infraction.getRadar().getId())
                .orElseThrow(() -> new IllegalArgumentException("Radar not found"));

        infraction.setRadar(radar);
        return infractionRepository.save(infraction);
    }
}



