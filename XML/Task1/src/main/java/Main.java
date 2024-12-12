import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

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
                    var attr = nextEvent.asCharacters().getData();;
                    updateTopWithValue(attr);
                }

                if (nextEvent.isEndElement()) {
                    EndElement endElement = nextEvent.asEndElement();

                    assert xmlStack.peek().equals(endElement.getName().getLocalPart());
                    xmlStack.pop();

                    if (endElement.getName().getLocalPart().equals("person")) {
                        lib.mergePerson();
                    }
                }
            }

        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }



        // lib.getPeople().forEach(Person::validate);
    }

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
}
