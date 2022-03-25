package com.example.air.application;

import com.example.air.infrastructure.api.seoul.SeoulAirQualityApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirQualityService {
    private final SeoulAirQualityApiCaller seoulAirQualityApiCaller;

    public AirQualityInfo getAirQualityInfo() {
        AirQualityInfo airQualityInfo = seoulAirQualityApiCaller.getAirQuality();
        // TODO: 자치구 검색 로직 추가 (시간 남는 경우)
        return airQualityInfo;
    }
}
