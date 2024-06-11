package com.fabrick.asteroid_path.service;

import com.fabrick.asteroid_path.model.AsteroidPath;
import com.fabrick.asteroid_path.service.external.NasaNeoLookupService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AsteroidPathServiceTest {

    private final NasaNeoLookupService nasaNeoLookupService = mock(NasaNeoLookupService.class);
    private final AsteroidPathService asteroidPathService = new AsteroidPathService(nasaNeoLookupService);

    @Test
    public void asteroidPathServiceOKTest() {
        when(nasaNeoLookupService.getAsteroidData(anyInt())).thenReturn(getNasaNeoLookupResponse());
        List<AsteroidPath> asteroidPathList =
                asteroidPathService.findAsteroidPath(3542519, "1900-01-01", LocalDate.now().toString());
        assertFalse(asteroidPathList.isEmpty());
    }

    @Test
    public void invalidAsteroidIdTest() {
        assertThrows(IllegalArgumentException.class,
                () -> asteroidPathService.findAsteroidPath(-10, "1900-01-01", "1999-01-01"));
    }

    @Test
    public void invalidDateFormatTest() {
        assertThrows(DateTimeParseException.class,
                () -> asteroidPathService.findAsteroidPath(3542519, "10-12-1960","10-12-2012"));
    }

    @Test
    public void invalidRangeOfDatesTest() {
        assertThrows(IllegalArgumentException.class,
                () -> asteroidPathService.findAsteroidPath(3542519, "1999-01-01","1900-01-01"));
    }

    private String getNasaNeoLookupResponse() {
        return """
                {
                  "links": {
                    "self": "http://api.nasa.gov/neo/rest/v1/neo/3542519?api_key=DEMO_KEY"
                  },
                  "id": "3542519",
                  "neo_reference_id": "3542519",
                  "name": "(2010 PK9)",
                  "designation": "2010 PK9",
                  "nasa_jpl_url": "https://ssd.jpl.nasa.gov/tools/sbdb_lookup.html#/?sstr=3542519",
                  "absolute_magnitude_h": 21.81,
                  "estimated_diameter": {
                    "kilometers": {
                      "estimated_diameter_min": 0.1154928176,
                      "estimated_diameter_max": 0.258249791
                    },
                    "meters": {
                      "estimated_diameter_min": 115.4928175848,
                      "estimated_diameter_max": 258.2497910326
                    },
                    "miles": {
                      "estimated_diameter_min": 0.0717638876,
                      "estimated_diameter_max": 0.1604689309
                    },
                    "feet": {
                      "estimated_diameter_min": 378.9134556449,
                      "estimated_diameter_max": 847.2762444114
                    }
                  },
                  "is_potentially_hazardous_asteroid": true,
                  "close_approach_data": [
                    {
                      "close_approach_date": "1900-06-01",
                      "close_approach_date_full": "1900-Jun-01 16:40",
                      "epoch_date_close_approach": -2195882400000,
                      "relative_velocity": {
                        "kilometers_per_second": "30.9354328365",
                        "kilometers_per_hour": "111367.5582113129",
                        "miles_per_hour": "69199.4697119127"
                      },
                      "miss_distance": {
                        "astronomical": "0.0445495565",
                        "lunar": "17.3297774785",
                        "kilometers": "6664518.761844655",
                        "miles": "4141139.931400039"
                      },
                      "orbiting_body": "Merc"
                    },
                    {
                      "close_approach_date": "1900-07-07",
                      "close_approach_date_full": "1900-Jul-07 22:08",
                      "epoch_date_close_approach": -2192752320000,
                      "relative_velocity": {
                        "kilometers_per_second": "31.7784421852",
                        "kilometers_per_hour": "114402.3918665533",
                        "miles_per_hour": "71085.1973239703"
                      }
                }
                ]
                }""";
    }
}
