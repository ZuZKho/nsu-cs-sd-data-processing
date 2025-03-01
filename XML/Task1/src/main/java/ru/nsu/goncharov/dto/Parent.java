package ru.nsu.goncharov.dto;

public class Parent extends FamilyConnection {
    public ParentType parentType = null;

        // TODO: _id can be UNKNOWN, maybe have to consider special cases
    public Parent(String _id) {
        id = _id;
    }

    public Parent(String _id, ParentType pt) {
        id = _id;
        parentType = pt;
    }

    public enum ParentType {
            Father,
            Mother
        }
}
