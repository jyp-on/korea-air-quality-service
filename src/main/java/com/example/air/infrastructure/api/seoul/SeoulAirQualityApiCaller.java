package com.example.air.infrastructure.api.seoul;

import com.example.air.application.AirQualityInfo;
import com.example.air.application.util.AirQualityGradeUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class SeoulAirQualityApiCaller {
    private final SeoulAirQualityApi seoulAirQualityApi;

    public SeoulAirQualityApiCaller(@Value("${api.seoul.base-url}") String baseUrl) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.seoulAirQualityApi = retrofit.create(SeoulAirQualityApi.class);
    }

    public AirQualityInfo getAirQuality() {
        try {
            var call = seoulAirQualityApi.getAirQuality();
            var response = call.execute().body();

            if (response == null || response.getResponse() == null) {
                throw new RuntimeException("[seoul] getAirQuality 응답값이 존재하지 않습니다.");
            }

            // 요청이 성공하는 경우 응답값 AirQualityInfo로 변환하여 리턴
            if (response.getResponse().isSuccess()) {
                log.info(response.toString());
                return convert(response);
            }

            throw new RuntimeException("[seoul] getAirQuality 응답이 올바르지 않습니다. header=" + response.getResponse().getResult());

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("[seoul] getAirQuality API error 발생! errorMessage=" + e.getMessage());
        }
    }

    // 서울시 공공 API에서 조회한 정보를 AirQualityInfo로 변환해주는 함수
    private AirQualityInfo convert(SeoulAirQualityApiDto.GetAirQualityResponse response) {
        List<SeoulAirQualityApiDto.Row> rows = response.getResponse().getRows();
        Double sidoPm10Avg = averagePm10(rows);
        String sidoPm10AvgGrade = AirQualityGradeUtil.getPm10Grade(sidoPm10Avg);
        List<AirQualityInfo.GuAirQualityInfo> guList = convert(rows);

        return AirQualityInfo.builder()
                .sido("서울시")
                .sidoPm10Avg(sidoPm10Avg)
                .sidoPm10AvgGrade(sidoPm10AvgGrade)
                .guList(guList)
                .build();
    }

    // TODO: 자치구 목록 정보 변환 함수
    private List<AirQualityInfo.GuAirQualityInfo> convert(List<SeoulAirQualityApiDto.Row> rows) {
        return null;
    }

    // TODO: 자치구 목록으로 pm10(미세먼지) 평균값을 구하는 함수
    private Double averagePm10(List<SeoulAirQualityApiDto.Row> rows) {
        return null;
    }
}
