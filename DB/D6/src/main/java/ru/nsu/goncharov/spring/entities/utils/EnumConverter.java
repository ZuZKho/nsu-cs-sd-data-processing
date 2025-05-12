package ru.nsu.goncharov.spring.entities.utils;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class EnumConverter implements Converter<String, SupportedLanguage> {
    @Override
    public SupportedLanguage convert(String source) {
        return SupportedLanguage.valueOf(source.toUpperCase());
    }
}
