package com.example.controller;

import com.example.model.Infraction;
import com.example.service.InfractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/infractions")
@Validated
public class InfractionController {
    private final InfractionService infractionService;

    @Autowired
    public InfractionController(InfractionService infractionService) {
        this.infractionService = infractionService;
    }

    @GetMapping
    public List<Infraction> getInfractions(
            @RequestParam(required = false) Long radarId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        if (radarId != null) {
            return infractionService.getInfractionsByRadar(radarId);
        } else if (start != null && end != null) {
            return infractionService.getInfractionsByDateRange(start, end);
        } else {
            return infractionService.getInfractionsByDateRange(LocalDateTime.now().minusMonths(1), LocalDateTime.now());
        }
    }

    @GetMapping("/count")
    public long countInfraction(
            @RequestParam(required = false) Long radarId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        if (radarId != null) {
            return infractionService.countInfractionsByRadar(radarId);
        } else if (start != null && end != null) {
            return infractionService.countInfractionsByDateRange(start, end);
        } else {
            return infractionService.countInfractionsByDateRange(LocalDateTime.now().minusMonths(1), LocalDateTime.now());
        }
    }

    @PostMapping
    public Infraction createInfraction(@Valid @RequestBody Infraction infraction) {
        return infractionService.saveInfraction(infraction);
    }
}





