package com.example.air.application;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class KoreaAirQualityServiceFactory {
    private final Map<Sido, KoreaAirQualityService> serviceMap = new HashMap<>();

    /**
     * 생성자를 통해 KoreaAirQualityService 를 상속받은 서비스를 key : value 형태로 저장함
     * { seoul : SeoulAirQualityApiCaller, busan : BusanAirQualityApiCaller }
     */
    public KoreaAirQualityServiceFactory(List<KoreaAirQualityService> services) {
        for (var service : services) {
            serviceMap.put(service.getSido(), service);
        }
    }


    public KoreaAirQualityService getService(Sido sido) {
        return Optional.of(serviceMap.get(sido)) //null 이 아닌경우 serviceMap에 sido값을 기준으로 service를 가져옴.
                .orElseThrow(() -> new RuntimeException("대기질 정보를 조회할 수 없는 시/도 입니다."));
    }
}
