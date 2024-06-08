package com.fabrick.asteroid_path.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AsteroidPathResponse {

    private List<AsteroidPath> asteroidPathList = new ArrayList<>();
}
