package com.example.controller;


import com.example.model.Infraction;
import com.example.model.Radar;
import com.example.service.InfractionService;
import com.example.service.RadarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/radars")
public class RadarController {
    private final RadarService radarService;

    @Autowired
    public RadarController(RadarService radarService) {
        this.radarService = radarService;
    }

    @GetMapping
    public List<Radar> findAll(){
        return radarService.findAll();
    }

    @GetMapping("/{id}")
    public Radar findById(@PathVariable Long id){
        return radarService.findById(id);
    }

    @PostMapping
    public Radar createRadar (@RequestBody Radar radar){
        return radarService.save(radar);
    }

    @PutMapping("/{id}")
    public Radar updateRadar (@PathVariable Long id, @RequestBody Radar radar){
        radar.setId(id);
        return radarService.save(radar);
    }

    @DeleteMapping("/{id}")
    public void deleteRadar(@PathVariable Long id){
        radarService.deleteById(id);
    }
}

