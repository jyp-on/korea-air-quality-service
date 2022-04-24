package com.example.air.application;

import com.example.air.infrastructure.api.busan.BusanAirQualityApiCaller;

public interface CallerService {
    AirQualityInfo getAirQuality();

    Sido getSido();
}
