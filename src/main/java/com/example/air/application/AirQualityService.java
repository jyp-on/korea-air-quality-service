package com.example.air.application;

import com.example.air.infrastructure.api.seoul.SeoulAirQualityApiCaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirQualityService {
    private final SeoulAirQualityApiCaller seoulAirQualityApiCaller;

    public AirQualityInfo getAirQualityInfo(Sido sido, String gu) {
        if (sido == Sido.seoul) {
            var airQualityInfo = seoulAirQualityApiCaller.getAirQuality();
            if (gu != null) {
                return airQualityInfo.searchByGu(gu);
            }
            return airQualityInfo;
        }

        throw new RuntimeException(sido + " 대기질 정보는 아직 준비중입니다.");
    }
}
