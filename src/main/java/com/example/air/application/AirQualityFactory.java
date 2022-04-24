package com.example.air.application;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class AirQualityFactory {

    private final Map<Sido,CallerService> callerServices = new HashMap<>();

    public AirQualityFactory(List<CallerService> callerServices) {
        //CallerService를 상속받는 bean이 없을 경우 IllegalArgumentException을 던진다.
        if (CollectionUtils.isEmpty(callerServices)) {
            throw new IllegalArgumentException("존재하는 CallerService가 없음");
        }

        for(CallerService callerService : callerServices){
            this.callerServices.put(callerService.getSido(), callerService);
        } //CallerService 의 구현체들을 for문 돌면서 sido값들과 해당 빈을 map에 담아준다.
    }

    //어느 sido가 들어오건 Map으로 각 시의 service들을 주입받음.
    public AirQualityInfo getAirQualityInfo(Sido sido, String gu) {
        if(sido == null) throw new RuntimeException(sido + " 대기질 정보는 아직 준비중입니다.");

        var airQualityInfo = callerServices.get(sido).getAirQuality(); //해당 bean의 airquality 추출
        if (gu != null) {
            return airQualityInfo.searchByGu(gu);
        }
        return airQualityInfo;
    }


}
