package ru.nsu.goncharov.dto;

public class Sibling extends FamilyConnection {
    public SiblingType siblingType = null;

    public Sibling(String _id, SiblingType _cht) {
        this.id = _id;
        this.siblingType = _cht;
    }

    public Sibling(String _id) {
        this.id = _id;
    }

    public enum SiblingType {
        Sister,
        Brother
    }
}
