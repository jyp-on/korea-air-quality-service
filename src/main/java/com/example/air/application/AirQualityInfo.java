package com.example.air.application;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AirQualityInfo {
    private String sido;
    private Double sidoPm10Avg; //시/도 평균 미세먼지 수치
    private String sidoPm10AvgGrade; //시/도 평균 미세먼지 등급
    private List<GuAirQualityInfo> guList; // 자치구 대기질 정보 리스트

    @Getter
    @Builder
    public static class GuAirQualityInfo {
        // TODO: 자치구 대기질 정보 명세서대로 파라미터 정의
        private String gu; //자치구 명
        private Integer pm10; //미세먼지 수치
        private Integer pm25; //초 미세먼지 수치
        private Double o3;
        private Double no2;
        private Double co;
        private Double so2;

        private String pm10Grade; //미세먼지 등급 ex) 좋음, 보통, 나쁨, 매우나쁨
        private String pm25Grade; //초 미세먼지 등급 ex) 좋음, 보통, 나쁨, 매우나쁨
        private String o3Grade;
        private String no2Grade;
        private String coGrade;
        private String so2Grade;

    }
}
