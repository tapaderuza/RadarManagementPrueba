package com.example.service;

import com.example.model.Radar;
import com.example.repository.RadarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RadarService {

    private final RadarRepository radarRepository;

    @Autowired
    public RadarService(RadarRepository radarRepository) {
        this.radarRepository = radarRepository;
    }

    public List<Radar> findAll() {
        return radarRepository.findAll();
    }

    public Radar findById(Long id) {
        return radarRepository.findById(id).orElse(null);
    }

    public Radar save(Radar radar) {
        Radar savedRadar = radarRepository.save(radar);
        return savedRadar;
    }

    public void deleteById(Long id) {
        radarRepository.deleteById(id);
    }
}

