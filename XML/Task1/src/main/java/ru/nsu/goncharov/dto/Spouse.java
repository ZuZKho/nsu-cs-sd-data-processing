package ru.nsu.goncharov.dto;

public class Spouse extends FamilyConnection {
    public SpouceType spouceType = null;

    public Spouse(String _id, SpouceType _cht) {
        this.id = _id;
        this.spouceType = _cht;
    }

    public Spouse(String _id) {
        this.id = _id;
    }

    public enum SpouceType {
        Husband,
        Wife
    }
}

