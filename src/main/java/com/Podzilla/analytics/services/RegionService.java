package com.Podzilla.analytics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Podzilla.analytics.repositories.RegionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.Podzilla.analytics.models.Region;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegionService {

    @Autowired
    private final RegionRepository regionRepository;

    public Region saveRegion(
            final String city,
            final String state,
            final String country,
            final String postalCode) {
        log.info("Saving region with city: {}, state: {},"
                + " country: {}, postalCode: {}",
                city, state, country, postalCode);
        Region region = Region.builder()
                .city(city)
                .state(state)
                .country(country)
                .postalCode(postalCode)
                .build();
        Region savedRegion = regionRepository.save(region);
        log.info("Region saved successfully with id: {}",
                savedRegion.getId());
        return savedRegion;
    }
}
