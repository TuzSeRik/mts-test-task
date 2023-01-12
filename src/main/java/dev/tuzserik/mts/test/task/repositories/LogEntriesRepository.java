package dev.tuzserik.mts.test.task.repositories;

import dev.tuzserik.mts.test.task.model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "logs", path = "logs")
public interface LogEntriesRepository extends JpaRepository<LogEntry, Integer> {

}
