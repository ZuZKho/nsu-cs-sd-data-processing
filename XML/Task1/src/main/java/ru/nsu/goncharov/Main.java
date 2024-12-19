package ru.nsu.goncharov;

import ru.nsu.goncharov.dto.Child;
import ru.nsu.goncharov.dto.Parent;
import ru.nsu.goncharov.dto.Sibling;
import ru.nsu.goncharov.dto.Spouse;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

import static ru.nsu.goncharov.dto.Parent.ParentType.Father;
import static ru.nsu.goncharov.dto.Parent.ParentType.Mother;
import static ru.nsu.goncharov.dto.Sibling.SiblingType.Brother;
import static ru.nsu.goncharov.dto.Sibling.SiblingType.Sister;
import static ru.nsu.goncharov.dto.Spouse.SpouceType.Husband;
import static ru.nsu.goncharov.dto.Spouse.SpouceType.Wife;

public class Main {

    private static Stack<String> xmlStack = new Stack<>();
    private static PersonLibrary lib = new PersonLibrary();

    public static void main(String[] args) {
        String path = "src/main/resources/people.xml";
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));

            while (reader.hasNext()) {
                XMLEvent nextEvent = reader.nextEvent();
                if (nextEvent.isStartElement()) {
                    StartElement startElement = nextEvent.asStartElement();
                    xmlStack.push(startElement.getName().getLocalPart());
                    if (startElement.getName().getLocalPart().equals("person")) {
                        lib.startNewPerson();
                    }

                    // First of all check attributes. There can be exactly 0 or 1 attributes.
                    var attributes = startElement.getAttributes();
                    if (attributes.hasNext()) {
                        var attr = attributes.next();
                        String attrName = attr.getName().getLocalPart();

                        switch (attrName) {
                            case "id":
                                updateTopWithId(attr.getValue());
                                break;
                            case "val", "value":
                                updateTopWithValue(attr.getValue());
                                break;
                            case "name":
                                updateTopWithName(attr.getValue());
                                break;
                            default:
                                System.out.println(attrName);
                                break;
                        }
                    }
                }

                if (nextEvent.isCharacters()) {
                    var attr = nextEvent.asCharacters().getData().trim();
                    if (!attr.isEmpty()) {
                        updateTopWithValue(attr);
                    }
                }

                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();

                    assert xmlStack.peek().equals(endElement.getName().getLocalPart());
                    xmlStack.pop();

                    if (endElement.getName().getLocalPart().equals("person")) {
                        lib.commitPerson();
                    }
                }
            }

        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        var results = lib.finish();
        System.out.println(results.size());
    }

    private static List<String> getCallStack() {
        ArrayList<String> callStack = new ArrayList<>();
        while(!xmlStack.isEmpty()) {
            callStack.add(xmlStack.pop());
        }

        callStack.reversed().forEach((String it) -> xmlStack.push(it));
        return callStack;
    }

    private static void updateTopWithValue(String value) {

        List<String> callStack = getCallStack();

        switch (callStack.get(0)) {
            case "gender":
                if (!callStack.get(1).equals("person")) {
                    System.out.println("Main gender error");
                    assert false;
                }

                lib.setGender(value);
                break;
            case "firstname", "first":
                if (!callStack.get(1).equals("person") && !callStack.get(1).equals("fullname")) {
                    System.out.println("Main firstname error");
                    assert false;
                }

                lib.setFirstname(value);
                break;
            case "family-name", "surname", "family":
                if (!callStack.get(1).equals("person") && !callStack.get(1).equals("fullname")) {
                    System.out.println("Main family-name error");
                    assert false;
                }

                lib.setLastname(value);
                break;
            case "id":
                if (!callStack.get(1).equals("person")) {
                    System.out.println("Main id error");
                    assert false;
                }

                lib.setId(value);
                break;
            case "siblings-number":
                if (!callStack.get(1).equals("person")) {
                    System.out.println("Main sibn error");
                    assert false;
                }

                lib.setSiblingsNumber(value);
                break;
            case "children-number":
                if (!callStack.get(1).equals("person")) {
                    System.out.println("Main chn error");
                    assert false;
                }

                lib.setChildrenNumber(value);
                break;
            case "child":
                if (!callStack.get(1).equals("children")) {
                    System.out.println("Main child error");
                    assert false;
                }

                lib.addChildByFullname(value);
                break;
            case "siblings":
                if (!callStack.get(1).equals("person")) {
                    System.out.println("Main siblings error");
                    assert false;
                }

                Arrays.stream(value.split(" ")).forEach((String sibling_id) -> lib.addSibling(new Sibling(sibling_id)));
                break;
            case "sister", "brother":
                if (!callStack.get(1).equals("siblings")) {
                    System.out.println("Main sister/brother error");
                    assert false;
                }

                lib.addSiblingByFullname(value, callStack.get(0).equals("brother") ? Brother : Sister);
                break;
            case "husband", "wife":
                if (!callStack.get(1).equals("person")) {
                    System.out.println("Main husband/wife error");
                    assert false;
                }

                lib.addSpouce(new Spouse(value, callStack.get(0).equals("wife") ? Wife : Husband));
                break;
            case "spouce":
                if (!callStack.get(1).equals("person")) {
                    System.out.println("Main spouce error");
                    assert false;
                }

                lib.addSpouceByFullname(value);
                break;
            case "parent":
                if (!callStack.get(1).equals("person")) {
                    System.out.println("Main parent error");
                    assert false;
                }

                lib.addParent(new Parent(value));
                break;
            case "father", "mother":
                if (!callStack.get(1).equals("person")) {
                    System.out.println("Main father/mother error");
                    assert false;
                }

                lib.addParentByFullname(value, callStack.get(0).equals("father") ? Father : Mother);
                break;
            default:
                System.out.println(callStack);
                break;
        }
    }

    private static void updateTopWithName(String fullname) {
        assert fullname.contains(" ") : "Fullname";

        List<String> callStack = getCallStack();

        switch (callStack.get(0)) {
            case "person":
                lib.setFullName(fullname);
                break;
            default:
                System.out.println(callStack);
                break;
        }
    }


    // Actually have the same logic as updateTopWithValue
    private static void updateTopWithId(String id) {
        List<String> callStack = getCallStack();

        switch (callStack.get(0)) {
            case "person":
                lib.setId(id);
                break;
            case "daughter", "son":
                if (!callStack.get(1).equals("children")) {
                    System.out.println("Main daughter/son error");
                    assert false;
                }

                lib.addChild(new Child(id, callStack.get(0).equals("son") ? Child.ChildType.Son : Child.ChildType.Daughter));
                break;
            default:
                System.out.println(callStack);
                break;
        }
    }
}
