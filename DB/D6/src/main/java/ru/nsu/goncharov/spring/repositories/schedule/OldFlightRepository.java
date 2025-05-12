package ru.nsu.goncharov.spring.repositories.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.nsu.goncharov.spring.entities.schedule.OldFlight;

import java.util.List;

public interface OldFlightRepository extends JpaRepository<OldFlight, Integer> {

    @Query("SELECT DISTINCT f.flightNo FROM OldFlight f")
    List<String> findAllFlightNumbers();

    @Query(value = """
        SELECT f FROM OldFlight f
        WHERE f.flightNo = :flightNo
        ORDER BY f.scheduledDeparture DESC
        LIMIT 7
        """)
    List<OldFlight> findLast7FlightsByFlightNo(String flightNo);
}
