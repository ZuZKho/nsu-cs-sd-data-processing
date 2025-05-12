package ru.nsu.goncharov.spring.repositories.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nsu.goncharov.spring.entities.schedule.NewFlight;

import java.util.List;

public interface NewFlightRepository extends JpaRepository<NewFlight, Integer> {

    @Query(value = """
        SELECT f FROM NewFlight f
        WHERE f.flightNo = :flightNo
        ORDER BY f.scheduledDeparture DESC
        LIMIT 1
        """)
    List<NewFlight> findLastFlightByFlightNo(String flightNo);
}
