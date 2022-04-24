package com.example.air.application;

import com.example.air.infrastructure.api.seoul.SeoulAirQualityApiCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeoulAirQualityService implements CallerService{

    private final SeoulAirQualityApiCaller seoulAirQualityApiCaller;
    @Override
    public AirQualityInfo getAirQuality() {
        return seoulAirQualityApiCaller.getAirQuality();
    }
    public Sido getSido(){
        return Sido.seoul;
    }
}
