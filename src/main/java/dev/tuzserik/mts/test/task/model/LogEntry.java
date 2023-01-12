package dev.tuzserik.mts.test.task.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.ZonedDateTime;

@Data @Entity
public class LogEntry {
    @Id
    @GeneratedValue
    private Integer id;
    private ZonedDateTime time = ZonedDateTime.now();
    private QueryType type;
    private String arguments;

}
