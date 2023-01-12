package dev.tuzserik.mts.test.task.controllers;

import dev.tuzserik.mts.test.task.model.Coordinates;
import dev.tuzserik.mts.test.task.model.LogEntry;
import dev.tuzserik.mts.test.task.model.QueryType;
import dev.tuzserik.mts.test.task.repositories.LogEntriesRepository;
import dev.tuzserik.mts.test.task.responses.api.GeocodingResponse;
import dev.tuzserik.mts.test.task.responses.api.WeatherResponse;
import dev.tuzserik.mts.test.task.responses.api.openweathermap.OwmWeatherResponse;
import dev.tuzserik.mts.test.task.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.reactive.function.client.WebClient;

@RestController()
@RequestMapping("/weather")
public class WeatherInfoController {
    @Value("${api.queries.coordinates.query}")
    private String coordinatesQuery;
    @Value("${api.queries.weather.query}")
    private String weatherQuery;

    private final WebClient weatherApi = WebClient.create();

    // Would be nice if Spring properties worked in Qualifier
    @Autowired @Qualifier("owmGeocodingResponse")
    private GeocodingResponse geocodingResponseClass;
    // Can use another conditional injecting though
    @Autowired @Qualifier("owmWeatherResponse")
    private WeatherResponse weatherResponseClass;

    @Autowired
    private LogEntriesRepository logEntriesRepository;

    @GetMapping()
    public ResponseEntity<OwmWeatherResponse> getLocationWeather(String locationName) {
        return new ResponseEntity<>(getCoordinatesObjectWeather(getLocationCoordinate(locationName)), HttpStatus.OK);
    }

    private Coordinates getLocationCoordinate(String locationName) {
        GeocodingResponse[] responses = weatherApi.get()
                                        .uri(coordinatesQuery, locationName).retrieve()
                                        .bodyToMono(Utils.asArray(geocodingResponseClass).getClass())
                                        .block();

        logEntriesRepository.saveAndFlush(
                new LogEntry()
                        .setType(QueryType.GEOCODING)
                        .setArguments(locationName)
        );

        if (responses == null || responses.length == 0) {
            throw new ResourceAccessException("Coordinates request returned empty response!");
        }

        GeocodingResponse result =  responses[0];
        Double lat = result.getLat();
        Double lon = result.getLon();

        return new Coordinates(lat, lon);
    }

    private OwmWeatherResponse getCoordinatesObjectWeather(Coordinates coordinates) {
        OwmWeatherResponse response = (OwmWeatherResponse) weatherApi.get()
                                        .uri(weatherQuery, coordinates.latitude(), coordinates.longitude())
                                        .retrieve().bodyToMono(weatherResponseClass.getClass()).block();

        logEntriesRepository.saveAndFlush(
                new LogEntry()
                        .setType(QueryType.WEATHER)
                        .setArguments(coordinates.latitude() + ", " + coordinates.longitude())
        );

        if (response == null) {
            throw new ResourceAccessException("Coordinates request returned empty response!");
        }

        return response;
    }

}
