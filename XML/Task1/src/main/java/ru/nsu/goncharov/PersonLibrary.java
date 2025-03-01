package ru.nsu.goncharov;

import ru.nsu.goncharov.dto.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Some common rules;
 * 1) Person always must have Id or Fullname. Id will be null in case of absence.
 * 2) Other fields that connected to id of other people can contain fullnames before finalization step.
 * 3) Finalization step have to change all fullnames in id's field to real ids.
 *
 */
public class PersonLibrary {

    private final HashMap<String, Person> personById = new HashMap<>();
    private final ArrayList<Person> personWithoutId = new ArrayList<>();
    private final HashSet<String> bannedFullNames = new HashSet<>();

    private Person currentPerson = null;

    private final HashMap<String, HashSet<String>> fullname_to_id = new HashMap<>();

    private final ArrayList<FamilyConnection> unknownIds = new ArrayList<>();

    public void startNewPerson() {
        currentPerson = new Person();
    }

    public void commitPerson() {
        // Proof that person have either Id or Fullname.
        boolean haveId = (currentPerson.id != null);
        boolean haveFullname = (currentPerson.firstName != null && currentPerson.lastName != null);
        assert haveId || haveFullname;

        // Check for existance in current library
        // If exists merge, if not exist just add.
        Person targetPerson = null;
        if (haveId) {
            if (personById.containsKey(currentPerson.id)) {
                targetPerson = personById.get(currentPerson.id);
            }
        }

        // if not exists, then add Person to HashMaps.
        if (targetPerson == null) {
            if (haveId) {
                personById.put(currentPerson.id, currentPerson);
            } else {
                personWithoutId.add(currentPerson);
            }
            targetPerson = currentPerson;
        } else {
            // If found Id or fullname add to needed HashMap too.
            // So the goal is on finalization step this hashmaps have to be equal
            targetPerson.merge(currentPerson);

            if (targetPerson.id != null) {
                personById.put(targetPerson.id, targetPerson);
            }
        }

        // Add connections between fullnames and ids
        if (targetPerson.id != null && targetPerson.firstName != null && targetPerson.lastName != null) {
            String fullname = targetPerson.getFullname();

            if (fullname_to_id.containsKey(fullname)) {
                fullname_to_id.get(fullname).add(targetPerson.id);
            } else {
                fullname_to_id.put(fullname, new HashSet<>());
                fullname_to_id.get(fullname).add(targetPerson.id);
            }
        }

        currentPerson = null;
    }

    private String getRealId(Person current, String possibleId1, String possibleId2) {
        Person person1 = personById.get(possibleId1);
        Person person2 = personById.get(possibleId2);

        if (current.siblingsNumber != null) {
            if (person1.siblingsNumber != null && person2.siblingsNumber != null && !person1.siblingsNumber.equals(person2.siblingsNumber)) {
                if (current.siblingsNumber.equals(person1.siblingsNumber)) {
                    return person1.id;
                } else
                if (current.siblingsNumber.equals(person2.siblingsNumber)) {
                    return person2.id;
                } else
                    assert false;
            }
        }

        if (current.siblingsNumber != null) {
            if (person1.siblingsNumber != null && !person1.siblingsNumber.equals(current.siblingsNumber)) {
                return person2.id;
            } else
            if (person2.siblingsNumber != null && !person2.siblingsNumber.equals(current.siblingsNumber)) {
                return person1.id;
            }
        }


        if (current.childrenNumber != null) {
            if (person1.childrenNumber != null && person2.childrenNumber != null && !person1.childrenNumber.equals(person2.childrenNumber)) {
                if (current.childrenNumber.equals(person1.childrenNumber)) {
                    return person1.id;
                } else
                if (current.childrenNumber.equals(person2.childrenNumber)) {
                    return person2.id;
                } else
                   assert false;
            }
        }


        if (current.childrenNumber != null) {
            if (person1.childrenNumber != null && !person1.childrenNumber.equals(current.childrenNumber)) {
                return person2.id;
            } else
            if (person2.childrenNumber != null && !person2.childrenNumber.equals(current.childrenNumber)) {
                return person1.id;
            }
        }

        if (current.spouse != null) {
            if (person1.spouse != null && person2.spouse != null && !person1.spouse.getId().equals(person2.spouse.getId())) {
                if (current.spouse.getId().equals(person1.spouse.getId())) {
                    return person1.id;
                } else
                if (current.spouse.getId().equals(person2.spouse.getId())) {
                    return person2.id;
                } else
                    assert false;
            }
        }

        if (current.spouse != null) {
            if (person1.spouse != null && !person1.spouse.getId().equals(current.spouse.getId())) {
                return person2.id;
            } else
            if (person2.spouse != null && !person2.spouse.getId().equals(current.spouse.getId())) {
                return person1.id;
            }
        }

        // Special for Kaylene Startz!  !!
        if (current.spouse != null && current.spouse.getId().equals(possibleId1)) {
            return possibleId2;
        }
        if (current.spouse != null && current.spouse.getId().equals(possibleId2)) {
            return possibleId1;
        }

        if (current.parents != null) {
            if (person1.parents != null && person2.parents != null) {
                boolean have1 = false;
                boolean have2 = false;

                for(var curparid : current.parents.stream().map(FamilyConnection::getId).toList()) {
                    for(var per1parid : person1.parents.stream().map(FamilyConnection::getId).toList()) {
                        if (curparid.equals(per1parid)) {
                            have1 = true;
                        }
                    }
                }

                for(var curparid : current.parents.stream().map(FamilyConnection::getId).toList()) {
                    for(var per2parid : person2.parents.stream().map(FamilyConnection::getId).toList()) {
                        if (curparid.equals(per2parid)) {
                            have2 = true;
                        }
                    }
                }

                if (have1 && !have2) {
                    return person1.id;
                } else if (have2 && !have1){
                    return person2.id;
                } else {
                    assert false;
                }
            }
        }

        // For Tonya Loschiavo
        if (current.children != null && !current.children.isEmpty()) {
            if (person1.childrenNumber != null && person1.childrenNumber == 0) {
                return possibleId2;
            }
            if (person2.childrenNumber != null && person2.childrenNumber == 0) {
                return possibleId1;
            }
        }

        if (current.siblings != null) {
            for(var sib : current.siblings) {
               for(var selfId : personById.get(sib.getId()).siblings.stream().map(FamilyConnection::getId).toList()) {
                   if (selfId.equals(possibleId1) && ! selfId.equals(possibleId2)) {
                       return possibleId1;
                   }
                   if (selfId.equals(possibleId2) && ! selfId.equals(possibleId1)) {
                       return possibleId2;
                   }
               }
            }
        }



        if (current.siblings != null) {
            if (person1.siblings != null && person2.siblings != null) {
                boolean have1 = false;
                boolean have2 = false;

                for(var cursibid : current.siblings.stream().map(FamilyConnection::getId).toList()) {
                    for(var per1sibid : person1.siblings.stream().map(FamilyConnection::getId).toList()) {
                        if (cursibid.equals(per1sibid)) {
                            have1 = true;
                        }
                    }
                }

                for(var cursibid : current.siblings.stream().map(FamilyConnection::getId).toList()) {
                    for(var per2sibid : person2.siblings.stream().map(FamilyConnection::getId).toList()) {
                        if (cursibid.equals(per2sibid)) {
                            have2 = true;
                        }
                    }
                }

                if (have1 && !have2) {
                    return person1.id;
                } else if (have2 && !have1){
                    return person2.id;
                }
            }
        }



        if (current.gender != null && person1.gender != null) {
            if (person1.gender != current.gender) {
                return possibleId2;
            }
        }

        if (current.gender != null && person2.gender != null) {
            if (person2.gender != current.gender) {
                return possibleId1;
            }
        }

        /*

    Can't detect id of this person.
    <person name="Janeen Robbinson">
    <children-number value="0"/>
    </person>
         */

        System.out.println("Still didn't find: " + current.getFullname());
        return null;
    }

    // will set all links that have fullnames to ids. And then merge all lists.
    public List<Person> finish() {
        ArrayList<Person> notProcessedWithoutId = new ArrayList<Person>();
        ArrayList<FamilyConnection> stillUnknownIds = new ArrayList<FamilyConnection>();
        for(var person: personWithoutId) {
            if (fullname_to_id.containsKey(person.getFullname()) && fullname_to_id.get(person.getFullname()).size() == 1) {
                var iter = fullname_to_id.get(person.getFullname()).iterator();
                person.id = iter.next();
                var targetPerson = personById.get(person.id);
                assert targetPerson != null;
                targetPerson.merge(person);
            } else {
                notProcessedWithoutId.add(person);
                System.out.println("[WARNING] Can't find id for " + person.getFullname());
            }
        }

        for (FamilyConnection unknownId : unknownIds) {
            if (fullname_to_id.containsKey(unknownId.getId()) && fullname_to_id.get(unknownId.getId()).size() == 1) {
                var iter = fullname_to_id.get(unknownId.getId()).iterator();
                unknownId.setId(iter.next());
            } else {
                stillUnknownIds.add(unknownId);
                System.out.println("[WARNING] Can't find id for " + unknownId.getId());
            }
        }

        // Try to find correct ids for notProcessed people
        for(int i = 0; i < 5; i++) {
            int cur = 0;
            for(var person : notProcessedWithoutId) {
                if (person.id != null) continue;
                var possibleIds = fullname_to_id.get(person.getFullname());
                assert possibleIds.size() == 2;
                var iter = possibleIds.iterator();

                person.id = getRealId(person, iter.next(), iter.next());
                cur++;
                if (person.id != null) {
                    var targetPerson = personById.get(person.id);
                    assert targetPerson != null;
                    targetPerson.merge(person);
                }
            }
            System.out.println(cur);
        }

        // System.out.println(stillUnknownIds.size());

        return personById.values().stream().map(Person::finish).toList();
    }

    public void setGender(String gender) {
        gender = gender.trim().toLowerCase();
        if (gender.equals("m") || gender.equals("male")) {
            assert currentPerson.gender != Gender.Female : "libGen";
            currentPerson.gender = Gender.Male;
        } else
        if (gender.equals("f") || gender.equals("female")) {
            assert currentPerson.gender != Gender.Male : "libGen";
            currentPerson.gender = Gender.Female;
        } else {
            assert false;
        }
    }

    public void setSiblingsNumber(String value) {
        assert currentPerson.siblingsNumber == null || currentPerson.siblingsNumber.equals(Integer.valueOf(value)) : "libSN";
        currentPerson.siblingsNumber = Integer.valueOf(value);
    }

    public void setChildrenNumber(String value) {
        assert currentPerson.childrenNumber == null || currentPerson.childrenNumber.equals(Integer.valueOf(value)) : "libCHN";
        currentPerson.childrenNumber = Integer.valueOf(value);
    }

    public void setFullName(String fullName) {
        fullName = fullName.replaceAll("[ \\t]+", " ").trim();

        String firstName = fullName.split(" ")[0];
        String lastName = fullName.split(" ")[1];
        assert(currentPerson.firstName == null || currentPerson.firstName.equals(firstName));
        assert(currentPerson.lastName == null || currentPerson.lastName.equals(lastName));

        currentPerson.firstName = firstName;
        currentPerson.lastName = lastName;
    }

    public void setId(String id) {
        id = id.trim();
        assert(currentPerson.id == null || currentPerson.id.equals(id));

        currentPerson.id = id;
    }

    public void addChild(Child child) {
        if (currentPerson.children == null) {
            currentPerson.children = new ArrayList<>();
        }
        currentPerson.children.add(child);
    }

    public void setFirstname(String value) {
        value = value.trim();
        assert(currentPerson.firstName == null || currentPerson.firstName.equals(value));

        currentPerson.firstName = value;
    }

    public void setLastname(String value) {
        value = value.trim();
        assert(currentPerson.lastName == null || currentPerson.lastName.equals(value));

        currentPerson.lastName = value;
    }

    public void addSibling(Sibling sibling) {
        if (currentPerson.siblings == null) {
            currentPerson.siblings = new ArrayList<>();
        }
        currentPerson.siblings.add(sibling);
    }

    public void addSpouce(Spouse spouse) {
        assert(currentPerson.spouse == null || currentPerson.spouse.equals(spouse));

        currentPerson.spouse = spouse;
    }

    public void addParent(Parent parent) {
        if (currentPerson.parents == null) {
            currentPerson.parents = new ArrayList<>();
        }
        currentPerson.parents.add(parent);
    }

    public void addChildByFullname(String fullName) {
        fullName = fullName.replaceAll("[ \\t]+", " ").trim();
        Child nwChild = new Child(fullName, null);
        addChild(nwChild);
        unknownIds.add(nwChild);
    }

    public void addSiblingByFullname(String fullName, Sibling.SiblingType siblingType) {
        fullName = fullName.replaceAll("[ \\t]+", " ").trim();
        Sibling nwSibling = new Sibling(fullName, siblingType);
        addSibling(nwSibling);
        unknownIds.add(nwSibling);
    }

    public void addSpouceByFullname(String fullName) {
        fullName = fullName.replaceAll("[ \\t]+", " ").trim();
        Spouse nwSpouce = new Spouse(fullName, null);
        addSpouce(nwSpouce);

        if (!fullName.equals("NONE")) {
            unknownIds.add(nwSpouce);
        }
    }

    public void addParentByFullname(String fullName, Parent.ParentType parentType) {
        fullName = fullName.replaceAll("[ \\t]+", " ").trim();
        Parent nwParent = new Parent(fullName, parentType);
        addParent(nwParent);
        unknownIds.add(nwParent);
    }
}
