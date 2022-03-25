package com.example.air.application;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AirQualityInfo {
    private String sido;
    private Double sidoPm10Avg;
    private String sidoPm10AvgGrade;
    private List<GuAirQualityInfo> guList;

    @Getter
    @Builder
    public static class GuAirQualityInfo {
        // TODO: 자치구 대기질 정보 명세서대로 파라미터 정의
    }
}
