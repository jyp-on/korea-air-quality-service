package com.example.air.application;


import com.example.air.infrastructure.api.busan.BusanAirQualityApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusanAirQualityService implements CallerService {

    private final BusanAirQualityApiCaller busanAirQualityApiCaller;

    @Override
    public AirQualityInfo getAirQuality() {
        return busanAirQualityApiCaller.getAirQuality();
    }
    public Sido getSido(){
        return Sido.busan;
    }
}
