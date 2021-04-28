package com.jwmu.common;

public enum Role {
    OWNER(0),
    TEAM_LEADER(1),
    USER(2);

    private final int value;

    Role(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
