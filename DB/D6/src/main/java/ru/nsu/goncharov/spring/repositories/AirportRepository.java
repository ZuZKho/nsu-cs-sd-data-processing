package ru.nsu.goncharov.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.goncharov.spring.entities.Airport;
import ru.nsu.goncharov.spring.entities.City;

import java.util.List;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {

    @Query("SELECT DISTINCT new ru.nsu.goncharov.spring.entities.City(a.city, a.timezone) " +
            "FROM Airport a GROUP BY a.city, a.timezone")
    List<City> findDistinctCities();


    @Query("SELECT a FROM Airport a " +
            "WHERE LOWER(a.city.ru) = LOWER(:cityName) OR " +
            "LOWER(a.city.en) = LOWER(:cityName)")
    List<Airport> findByCityInAnyLanguage(@Param("cityName") String cityName);
}
