package com.example.air.interfaces.api;

import com.example.air.application.AirQualityService;
import com.example.air.application.AirQualityInfo;
import com.example.air.application.Sido;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.Path;

@RestController
@RequiredArgsConstructor
public class AirQualityApiController {
    private final AirQualityService airQualityService;

    /// TODO: 시도와 구정보를 parameter 로 받는 GET API 작성
    @GetMapping("/api/v1/air-quality/{sidoCode}")
    public AirQualityInfo getAirQualityInfo(@PathVariable("sidoCode") Sido sidoCode,
                                            @RequestParam(required = false, defaultValue = "All") String gu) {

        return airQualityService.getAirQualityInfo(sidoCode, gu);
    }
}
