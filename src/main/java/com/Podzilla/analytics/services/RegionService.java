package com.Podzilla.analytics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Podzilla.analytics.repositories.RegionRepository;

import lombok.RequiredArgsConstructor;
import com.Podzilla.analytics.models.Region;



@Service
@RequiredArgsConstructor
public class RegionService {

    @Autowired
    private final RegionRepository regionRepository;

    public Region saveRegion(
        final String city,
        final String state,
        final String country,
        final String postalCode
    ) {
        Region region = Region.builder()
            .city(city)
            .state(state)
            .country(country)
            .postalCode(postalCode)
            .build();
        return regionRepository.save(region);
    }
}
