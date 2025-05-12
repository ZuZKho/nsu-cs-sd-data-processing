package ru.nsu.goncharov.spring.entities.booking;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

public class TicketIdGenerator implements IdentifierGenerator {

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        UUID uuid = UUID.randomUUID();

        // Combine most and least significant bits
        BigInteger bigInt = new BigInteger(1,
                ByteBuffer.allocate(16)
                        .putLong(uuid.getMostSignificantBits())
                        .putLong(uuid.getLeastSignificantBits())
                        .array());

        // Take first 10 digits after "111" prefix
        String numericId = bigInt.toString().substring(0, 10);
        return "111" + numericId;
    }
}

