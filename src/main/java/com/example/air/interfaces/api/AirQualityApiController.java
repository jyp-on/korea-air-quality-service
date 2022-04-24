package com.example.air.interfaces.api;

import com.example.air.application.AirQualityFactory;
import com.example.air.application.AirQualityInfo;
import com.example.air.application.SeoulAirQualityService;
import com.example.air.application.Sido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/air-quality")
public class AirQualityApiController {
    private final AirQualityFactory airQualityFactory;

    @GetMapping("/{sidoCode}")
    public AirQualityInfo getAirQualityInfo(@PathVariable("sidoCode") Sido sidoCode,
                                            @RequestParam(required = false) String gu) {
        return airQualityFactory.getAirQualityInfo(sidoCode, gu);
    }
}
