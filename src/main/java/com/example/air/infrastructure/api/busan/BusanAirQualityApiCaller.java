package com.example.air.infrastructure.api.busan;

import com.example.air.application.AirQualityInfo;
import com.example.air.application.KoreaAirQualityService;
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
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BusanAirQualityApiCaller implements KoreaAirQualityService {
    private final BusanAirQualityApi busanAirQualityApi;

    public BusanAirQualityApiCaller(@Value("${api.busan.base-url}") String baseUrl) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        this.busanAirQualityApi = retrofit.create(BusanAirQualityApi.class);
    }

    @Override
    public Sido getSido() {
        return Sido.busan;
    }

    @Override
    public AirQualityInfo getAirQualityInfo() {
        try {
            var call = busanAirQualityApi.getAirQuality();
            var response = call.execute().body();

            if (response == null || response.getResponse() == null) {
                throw new RuntimeException("[busan] getAirQuality 응답값이 존재하지 않습니다.");
            }

            if (response.getResponse().isSuccess()) {
                log.info(response.toString());
                return convert(response);
            }

            throw new RuntimeException("[busan] getAirQuality 응답이 올바르지 않습니다. header=" + response.getResponse().getHeader());

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("[busan] getAirQuality API error 발생! errorMessage=" + e.getMessage());
        }
    }

    private AirQualityInfo convert(BusanAirQualityApiDto.GetAirQualityResponse response) {
        var items = response.getResponse().getItems();
        var sidoPm10Avg = averagePm10(items);
        var sidoPm10AvgGrade = AirQualityGradeUtil.getPm10Grade(sidoPm10Avg);
        var guList = convert(items);

        return AirQualityInfo.builder()
                .sido(Sido.busan.getDescription())
                .sidoPm10Avg(sidoPm10Avg)
                .sidoPm10AvgGrade(sidoPm10AvgGrade)
                .guList(guList)
                .build();
    }

    private List<AirQualityInfo.GuAirQualityInfo> convert(List<BusanAirQualityApiDto.Item> items) {
        return items.stream()
                .map(item -> new AirQualityInfo.GuAirQualityInfo(
                        item.getSite(),
                        item.getPm10(),
                        item.getPm25(),
                        item.getO3(),
                        item.getO3(),
                        item.getCo(),
                        item.getSo2())
                )
                .collect(Collectors.toList());
    }

    private Double averagePm10(List<BusanAirQualityApiDto.Item> items) {
        return items.stream()
                .mapToInt(BusanAirQualityApiDto.Item::getPm10)
                .average()
                .orElse(Double.NaN);
    }
}
