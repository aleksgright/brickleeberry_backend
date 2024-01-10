package com.example.brickleberry_backend.Enums;

public enum Role {
    MANAGER("Управляющий"),
    WAREHOUSE_DEPARTMENT("Хозотдел"),
    INSPECTOR("Инспектор"),
    SCIENCE_DEPARTMENT("Научный отдел");


    private final String name;

    private Role(String name) {
        this.name = name;
    }

    public static Role valueOfName(String name) {
        for (Role e : values()) {
            if (e.name.equals(name)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
