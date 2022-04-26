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


    @Cacheable(value = "code", key = "#sido")
    public AirQualityInfo getAirQualityInfo(Sido sido, String gu) {
        KoreaAirQualityService service = koreaAirQualityServiceFactory.getService(sido);

        var airQualityInfo = service.getAirQualityInfo();
        if (gu != null) {
            return airQualityInfo.searchByGu(gu);
        }
        return airQualityInfo;
    }
}
