package com.example.air.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AirQualityService {
    private final KoreaAirQualityServiceFactory koreaAirQualityServiceFactory;


    @Cacheable(value = "sido", key = "#sido")
    public AirQualityInfo getAirQualityInfo(Sido sido) {
        KoreaAirQualityService service = koreaAirQualityServiceFactory.getService(sido);

        var airQualityInfo = service.getAirQualityInfo();

        return airQualityInfo;
    }

    @Cacheable(value = "sido_gu", key = "#gu")
    public AirQualityInfo getAirQualityInfo_Gu(Sido sido, String gu) {
        KoreaAirQualityService service = koreaAirQualityServiceFactory.getService(sido);

        var airQualityInfo = service.getAirQualityInfo();

        return airQualityInfo.searchByGu(gu);
    }
}
