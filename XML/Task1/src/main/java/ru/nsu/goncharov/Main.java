package ru.nsu.goncharov;

import ru.nsu.goncharov.autogen.*;
import ru.nsu.goncharov.dto.*;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

import static ru.nsu.goncharov.dto.Parent.ParentType.Father;
import static ru.nsu.goncharov.dto.Parent.ParentType.Mother;
import static ru.nsu.goncharov.dto.Sibling.SiblingType.Brother;
import static ru.nsu.goncharov.dto.Sibling.SiblingType.Sister;
import static ru.nsu.goncharov.dto.Spouse.SpouceType.Husband;
import static ru.nsu.goncharov.dto.Spouse.SpouceType.Wife;

public class Main {

    private static final Stack<String> xmlStack = new Stack<>();
    private static final PersonLibrary lib = new PersonLibrary();

    public static void main(String[] args) {

        var result = collectData();

        // Transfer
        People people = transfer(result);
        HashMap<String, PersonType> hashMap = new HashMap<>();
        people.getPerson().forEach(it -> {
            hashMap.put(it.getId(), it);
        });
        addConnections(people, hashMap, result);

        Exporter.export("src/main/resources/result.xml", people);
    }

    private static People transfer(List<Person> data) {
        People people = new People();
        data.forEach(it -> {
            PersonType person = new PersonType();
            person.setId(it.id);
            if (it.gender != null) {
                person.setGender(it.gender == Gender.Male ? GenderType.MALE : GenderType.FEMALE);
            }
            if (it.firstName != null) {
                person.setFirstName(it.firstName);
            }
            if (it.lastName != null) {
                person.setLastName(it.lastName);
            }
            if (it.siblingsNumber != null) {
                person.setSiblingsNumber(new BigInteger(String.valueOf(it.siblingsNumber)));
            }
            if (it.childrenNumber != null) {
                person.setChildrenNumber(new BigInteger(String.valueOf(it.childrenNumber)));
            }

            people.getPerson().add(person);
        });

        return people;
    }


    private static void addConnections(People people, HashMap<String, PersonType> hashMap, List<Person> data) {

        // Add siblings
        data.forEach(it -> {

            if (it.siblings != null) {
                SiblingsType siblings = new SiblingsType();

                for (Sibling sibling : it.siblings) {

                    FamilyConnectionType connection = new FamilyConnectionType();
                    if (hashMap.get(sibling.getId()) == null) {
                        continue;
                    }

                    connection.setId(hashMap.get(sibling.getId()));

                    if (sibling.siblingType == Sister || hashMap.get(sibling.getId()).getGender() == GenderType.FEMALE) {
                        siblings.getSister().add(connection);
                    } else if (sibling.siblingType == Brother || hashMap.get(sibling.getId()).getGender() == GenderType.MALE) {
                        siblings.getBrother().add(connection);
                    } else {
                        System.out.println("Internal sibling gender error.");
                    }
                }

                hashMap.get(it.id).setSiblings(siblings);
            }

            if (it.children != null) {
                ChildrenType children = new ChildrenType();

                for (Child child : it.children) {

                    FamilyConnectionType connection = new FamilyConnectionType();
                    if (hashMap.get(child.getId()) == null) {
                        continue;
                    }

                    connection.setId(hashMap.get(child.getId()));

                    if (child.childType == Child.ChildType.Daughter || hashMap.get(child.getId()).getGender() == GenderType.FEMALE) {
                        children.getDaughter().add(connection);
                    } else if (child.childType == Child.ChildType.Son || hashMap.get(child.getId()).getGender() == GenderType.MALE) {
                        children.getSon().add(connection);
                    } else {
                        System.out.println("Internal child gender error.");
                    }
                }

                hashMap.get(it.id).setChildren(children);
            }

            if (it.parents != null && !it.parents.isEmpty()) {
                ParentsType parents = new ParentsType();

                for (Parent parent : it.parents) {

                    FamilyConnectionType connection = new FamilyConnectionType();
                    if (hashMap.get(parent.getId()) == null) {
                        if (!parent.getId().equals("UNKNOWN")) {
                        }
                        continue;
                    }

                    connection.setId(hashMap.get(parent.getId()));

                    if (parent.parentType == Mother || hashMap.get(parent.getId()).getGender() == GenderType.FEMALE) {
                        parents.setMother(connection);
                    } else if (parent.parentType == Father || hashMap.get(parent.getId()).getGender() == GenderType.MALE) {
                        parents.setFather(connection);
                    } else {
                        System.out.println("Internal child gender error.");
                    }
                }

                hashMap.get(it.id).setParents(parents);
            }

            if (it.spouse != null) {
                FamilyConnectionType connection = new FamilyConnectionType();
                if (hashMap.get(it.spouse.getId()) == null) {
                    if (!it.spouse.getId().equals("NONE")) {
                        // System.out.println("Spouce skipped: " + it.spouse.getId());
                    }
                } else {
                    connection.setId(hashMap.get(it.spouse.getId()));

                    if (it.spouse.spouceType == Wife || hashMap.get(it.spouse.getId()).getGender() == GenderType.FEMALE) {
                        hashMap.get(it.id).setWife(connection);
                    } else if (it.spouse.spouceType == Husband || hashMap.get(it.spouse.getId()).getGender() == GenderType.MALE) {
                        hashMap.get(it.id).setHusband(connection);
                    } else {
                        System.out.println("Internal spouce gender error.");
                    }
                }
            }
        });
    }

    private static List<Person> collectData() {
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

        return lib.finish();
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
