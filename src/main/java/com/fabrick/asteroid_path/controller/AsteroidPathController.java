package com.fabrick.asteroid_path.controller;

import com.fabrick.asteroid_path.model.AsteroidPathResponse;
import com.fabrick.asteroid_path.service.AsteroidPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/fabrick/v1.0")
public class AsteroidPathController {

    private final AsteroidPathService asteroidPathService;

    @Autowired
    public AsteroidPathController(AsteroidPathService asteroidPathService) {
        this.asteroidPathService = asteroidPathService;
    }

    @GetMapping("/asteroids/{asteroidId}/paths")
    public AsteroidPathResponse getAsteroidPaths(@PathVariable int asteroidId,
                                                 @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now().minusYears(100)}") LocalDate fromDate,
                                                 @RequestParam(required = false, defaultValue = "#{T(java.time.LocalDate).now()}") LocalDate toDate) {

        return asteroidPathService.findAsteroidPath(asteroidId, fromDate, toDate);
    }
}
