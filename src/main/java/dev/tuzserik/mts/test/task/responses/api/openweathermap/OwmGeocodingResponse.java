package dev.tuzserik.mts.test.task.responses.api.openweathermap;

import dev.tuzserik.mts.test.task.responses.api.GeocodingResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("owmGeocodingResponse") @Getter @Setter
public class OwmGeocodingResponse extends GeocodingResponse {
    private String name;
    private Map<String, String> local_names;
    private Double lat;
    private Double lon;
    private String country;
    private String state;
}
