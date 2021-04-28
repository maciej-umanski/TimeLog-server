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

    public String getName(){

        switch (value) {
            case 0 -> {
                return "Owner";
            }
            case 1 -> {
                return "Team_leader";
            }
            case 2 -> {
                return "User";
            }
            default -> {
                return "UNKNOWN";
            }
        }
    }
}
