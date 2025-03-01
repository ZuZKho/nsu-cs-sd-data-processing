package ru.nsu.goncharov;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import ru.nsu.goncharov.autogen.*;
import ru.nsu.goncharov.dto.Spouse;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.math.BigInteger;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class Exporter {

    public static void export(String filename, People people) {
        try {
            // Marshal the Java objects into XML
            JAXBContext context = JAXBContext.newInstance(People.class);
            Marshaller marshaller = context.createMarshaller();
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("src/main/resources/xsd/schema.xsd"));
            marshaller.setSchema(schema);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Print the XML to the console
            marshaller.marshal(people, new FileOutputStream(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
