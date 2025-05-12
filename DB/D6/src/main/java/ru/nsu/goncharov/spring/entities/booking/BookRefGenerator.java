package ru.nsu.goncharov.spring.entities.booking;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.UUID;

public class BookRefGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        // Generate a random alphanumeric string of length 5
        String randomPart = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);

        // Prepend "G" to make it 6 characters long
        return "G" + randomPart.toUpperCase();
    }
}
