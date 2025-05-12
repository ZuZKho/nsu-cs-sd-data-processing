package ru.nsu.goncharov.spring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.goncharov.spring.entities.utils.Language;

@Data
@AllArgsConstructor
public class City {
    Language name;
    String timezone;
}
