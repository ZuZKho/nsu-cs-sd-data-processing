package ru.nsu.goncharov.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Person {
    public String id = null;
    public String firstName = null;
    public String lastName = null;
    public Gender gender = null;
    public Spouse spouse = null;
    public Integer childrenNumber = null;
    public Integer siblingsNumber = null;

    // Delete duplicates during finalization
    public List<Parent> parents;
    public List<Child> children;
    public List<Sibling> siblings;


    public String getFullname() {
        return firstName + " " + lastName;
    }

    public void merge(Person person) {
        // Just skip extra checks...
        if (id == null) {
            id = person.id;
        }
        if (firstName == null) {
            firstName = person.firstName;
        }
        if (lastName == null) {
            lastName = person.lastName;
        }
        if (gender == null) {
            gender = person.gender;
        }
        if (spouse == null) {
            spouse = person.spouse;
        }
        if (childrenNumber == null) {
            childrenNumber = person.childrenNumber;
        }
        if (siblingsNumber == null) {
            siblingsNumber = person.siblingsNumber;
        }
        if (parents == null) {
            parents = person.parents;
        } else {
            if (person.parents != null) {
                parents.addAll(person.parents);
            }
        }
        if (children == null) {
            children = person.children;
        } else {
            if (person.children != null) {
                children.addAll(person.children);
            }
        }
        if (siblings == null) {
            siblings = person.siblings;
        } else {
            if (person.siblings != null) {
                siblings.addAll(person.siblings);
            }
        }
    }

    private List<Sibling> removeDuplicatesSib(List<Sibling> lst) {
        HashSet<String> seenIds = new HashSet<>();
        List<Sibling> uniquePersonList = new ArrayList<>();

        for (var conn : lst) {
            if (seenIds.add(conn.getId())) {
                uniquePersonList.add(conn);
            }
        }

        return uniquePersonList;
    }


    private List<Parent> removeDuplicatesPar(List<Parent> lst) {
        HashSet<String> seenIds = new HashSet<>();
        List<Parent> uniquePersonList = new ArrayList<>();

        for (var conn : lst) {
            if (seenIds.add(conn.getId())) {
                uniquePersonList.add(conn);
            }
        }

        return uniquePersonList;
    }

    private List<Child> removeDuplicatesChild(List<Child> lst) {
        HashSet<String> seenIds = new HashSet<>();
        List<Child> uniquePersonList = new ArrayList<>();

        for (var conn : lst) {
            if (seenIds.add(conn.getId())) {
                uniquePersonList.add(conn);
            }
        }

        return uniquePersonList;
    }

    public Person finish() {
        // Validate and deduplicate
        if (siblings != null) {
            siblings = removeDuplicatesSib(siblings);
        }
        if (parents != null) {
            parents = removeDuplicatesPar(parents);
        }
        if (children != null) {
            children = removeDuplicatesChild(children);
        }

        if (!validate()) {
            System.out.println("[WARNING] validation fall:" + id);
        }

        return this;
    }

    boolean validate() {
        if (childrenNumber != null && children != null && children.size() != childrenNumber) return false;
        if (siblingsNumber != null && siblings != null && siblings.size() != siblingsNumber) return false;

        return true;
    }
}
