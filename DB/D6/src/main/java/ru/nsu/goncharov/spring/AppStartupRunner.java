package ru.nsu.goncharov.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.nsu.goncharov.spring.services.FlightGenerationService;

@Component
public class AppStartupRunner implements CommandLineRunner {

    @Autowired
    FlightGenerationService flightGenerationService;

    @Override
    public void run(String... args) throws Exception {
        // Your startup logic here
        flightGenerationService.updateFlights();
        System.out.println("Application started - executing startup code");
    }
}
