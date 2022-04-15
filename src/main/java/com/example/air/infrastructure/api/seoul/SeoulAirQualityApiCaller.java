package com.example.air.infrastructure.api.seoul;

import com.example.air.application.AirQualityInfo;
import com.example.air.application.Sido;
import com.example.air.application.util.AirQualityGradeUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
            String date = getDateAnHourAgo();
            var call = seoulAirQualityApi.getAirQuality(date);
            var response = call.execute().body();

            if (response == null || response.getResponse() == null) {
                throw new RuntimeException("[seoul] getAirQuality 응답값이 존재하지 않습니다.");
            }

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

    private String getDateAnHourAgo() {
        return LocalDateTime.now().minusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    private AirQualityInfo convert(SeoulAirQualityApiDto.GetAirQualityResponse response) {
        var rows = response.getResponse().getRows();
        var sidoPm10Avg = averagePm10(rows);
        var sidoPm10AvgGrade = AirQualityGradeUtil.getPm10Grade(sidoPm10Avg);
        var guList = convert(rows);

        return AirQualityInfo.builder()
                .sido(Sido.seoul.getDescription())
                .sidoPm10Avg(sidoPm10Avg)
                .sidoPm10AvgGrade(sidoPm10AvgGrade)
                .guList(guList)
                .build();
    }

    private List<AirQualityInfo.GuAirQualityInfo> convert(List<SeoulAirQualityApiDto.Row> rows) {
        return rows.stream()
                .map(row -> new AirQualityInfo.GuAirQualityInfo(
                        row.getSite(),
                        row.getPm10(),
                        row.getPm25(),
                        row.getO3(),
                        row.getO3(),
                        row.getCo(),
                        row.getSo2())
                )
                .collect(Collectors.toList());
    }

    private Double averagePm10(List<SeoulAirQualityApiDto.Row> rows) {
        return rows.stream()
                .mapToInt(SeoulAirQualityApiDto.Row::getPm10)
                .average()
                .orElse(Double.NaN);
    }
}
