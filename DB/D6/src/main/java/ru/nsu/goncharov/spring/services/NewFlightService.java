package ru.nsu.goncharov.spring.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.goncharov.spring.entities.schedule.NewFlight;
import ru.nsu.goncharov.spring.repositories.schedule.NewFlightRepository;

@Service
@Transactional
@AllArgsConstructor
public class NewFlightService {

    private NewFlightRepository newFlightRepository;

    public NewFlight saveNewFlight(NewFlight newFlight) {
        return newFlightRepository.save(newFlight);
    }

}
