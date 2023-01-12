package dev.tuzserik.mts.test.task.responses.api.openweathermap;

import dev.tuzserik.mts.test.task.responses.api.WeatherResponse;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("owmWeatherResponse") @Getter @Setter
public class OwmWeatherResponse extends WeatherResponse {
    private Coordinates coord;
    private List<Weather> weather;
    private String base;
    private MainInfo main;
    private Integer visibility;
    private Wind wind;
    private Clouds clouds;
    private Integer dt;
    private SystemInfo sys;
    private Integer timezone;
    private Integer id;
    private String name;
    private Integer cod;

    @Data
    public static class Coordinates {
        private Double lat;
        private Double lon;
    }

    @Data
    public static class Weather {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class MainInfo {
        private Double temp;
        private Double feels_like;
        private Double temp_min;
        private Double temp_max;
        private Integer pressure;
        private Integer humidity;
    }

    @Data
    public static class Wind {
        private Integer speed;
        private Integer deg;
    }

    @Data
    public static class Clouds {
        private Integer all;
    }

    @Data
    public static class SystemInfo {
        private Integer type;
        private Integer id;
        private String country;
        private Integer sunrise;
        private Integer sunset;
    }

}
