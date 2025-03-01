package ru.nsu.goncharov.dto;

public class Child extends FamilyConnection {
    public ChildType childType = null;

    public Child(String _id, ChildType _cht) {
        this.id = _id;
        this.childType = _cht;
    }

    public Child(String _id) {
        this.id = _id;
    }

    public enum ChildType {
        Daughter,
        Son
    }
}
