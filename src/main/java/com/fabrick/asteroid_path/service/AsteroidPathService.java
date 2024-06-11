package com.fabrick.asteroid_path.service;

import com.fabrick.asteroid_path.model.ApproachData;
import com.fabrick.asteroid_path.model.AsteroidPath;
import com.fabrick.asteroid_path.model.NasaNeoLookupData;
import com.fabrick.asteroid_path.service.external.NasaNeoLookupService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AsteroidPathService {

    private final NasaNeoLookupService nasaNeoLookupService;

    @Autowired
    public AsteroidPathService(NasaNeoLookupService nasaNeoLookupService) {
        this.nasaNeoLookupService = nasaNeoLookupService;
    }

    public List<AsteroidPath> findAsteroidPath(int asteroidId, String fromDate, String toDate) {

        validateInput(asteroidId, fromDate, toDate);

        NasaNeoLookupData nasaNeoLookupData = callNasaNeoLookupService(asteroidId);

        return findAsteroidPaths(nasaNeoLookupData.getApproachDataList(), LocalDate.parse(fromDate), LocalDate.parse(toDate));
    }

    private void validateInput(int asteroidId, String fromDate, String toDate) {
        if (asteroidId <= 0) {
            throw new IllegalArgumentException("Invalid asteroid ID: " + asteroidId);
        }

        if (LocalDate.parse(fromDate).isAfter(LocalDate.parse(toDate))) {
            throw new IllegalArgumentException("Date " + fromDate + " is after " + toDate);
        }
    }

    @Cacheable("nasaNeoLookupData")
    private NasaNeoLookupData callNasaNeoLookupService(int asteroidId) {
        String neoLookupResponse = nasaNeoLookupService.getAsteroidData(asteroidId);
        return new Gson().fromJson(neoLookupResponse, NasaNeoLookupData.class);
    }

    private List<AsteroidPath> findAsteroidPaths(List<ApproachData> approachDataList, LocalDate fromDate, LocalDate toDate) {
        List<AsteroidPath> asteroidPathList = new ArrayList<>();
        approachDataList.sort(Comparator.comparing(a -> LocalDate.parse(a.getCloseApproachDate())));

        String currentPlanet = null;
        LocalDate currentDate = null;

        for (ApproachData approachData : approachDataList) {
            String orbitingBody = approachData.getOrbitingBody();
            LocalDate closeApproachDate = LocalDate.parse(approachData.getCloseApproachDate());

            if (currentPlanet != null && !currentPlanet.equals(orbitingBody)) {
                if ( (currentDate.isAfter(fromDate) || currentDate.isEqual(fromDate)) &&
                        (closeApproachDate.isBefore(toDate) || closeApproachDate.isEqual(toDate)) ) {
                    asteroidPathList.add(new AsteroidPath(currentPlanet, orbitingBody, currentDate, closeApproachDate));
                }
            }
            currentPlanet = orbitingBody;
            currentDate = closeApproachDate;
        }
        return asteroidPathList;
    }
}
