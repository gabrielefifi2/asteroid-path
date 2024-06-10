package com.fabrick.asteroid_path.service.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NasaNeoLookupService {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String apiKey;

    @Autowired
    public NasaNeoLookupService(RestTemplate restTemplate,
                                @Value("${nasa.neo-lookup.api.base-url}") String baseUrl,
                                @Value("${nasa.neo-lookup.api.api-key}") String apiKey) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    public String getAsteroidData(int asteroidId) {
        String url = String.format("%s/%s?api_key=%s", baseUrl, asteroidId, apiKey);
        return restTemplate.getForObject(url, String.class);
    }
}
