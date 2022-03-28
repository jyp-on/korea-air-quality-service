package com.example.air.application.util;

public class AirQualityGradeUtil {
    private AirQualityGradeUtil() {
    }

    // TODO: pm25 등급정보 구하는 함수
    public static String getPm25Grade(Double pm25) {
        if (pm25 >= 76){
            return "매우나쁨";
        }
        else if(pm25 >= 36){
            return "나쁨";
        }
        else if(pm25 >= 16){
            return "보통";
        }
        else{
            return "좋음";
        }
    }

    // TODO: pm10 등급정보 구하는 함수
    public static String getPm10Grade(Double pm10) {
        if (pm10 >= 151){
            return "매우나쁨";
        }
        else if(pm10 >= 81){
            return "나쁨";
        }
        else if(pm10 >= 31){
            return "보통";
        }
        else{
            return "좋음";
        }
    }

    // TODO: o3 등급정보 구하는 함수
    public static String getO3Grade(Double o3) {
        if (o3 >= 0.151){
            return "매우나쁨";
        }
        else if(o3 >= 0.091){
            return "나쁨";
        }
        else if(o3 >= 0.031){
            return "보통";
        }
        else{
            return "좋음";
        }
    }

    // TODO: no2 등급정보 구하는 함수
    public static String getNo2Grade(Double no2) {
        if (no2 >= 0.201){
            return "매우나쁨";
        }
        else if(no2 >= 0.061){
            return "나쁨";
        }
        else if(no2 >= 0.031){
            return "보통";
        }
        else{
            return "좋음";
        }
    }

    // TODO: co 등급정보 구하는 함수
    public static String getCoGrade(Double co) {
        if (co >= 15.01){
            return "매우나쁨";
        }
        else if(co >= 9.01){
            return "나쁨";
        }
        else if(co >= 2.01){
            return "보통";
        }
        else{
            return "좋음";
        }
    }

    // TODO: so2 등급정보 구하는 함수
    public static String getSo2Grade(Double so2) {
        if (so2 >= 0.151){
            return "매우나쁨";
        }
        else if(so2 >= 0.051){
            return "나쁨";
        }
        else if(so2 >= 0.021){
            return "보통";
        }
        else{
            return "좋음";
        }
    }
}
