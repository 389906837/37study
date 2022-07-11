package com.antbox.rfidmachine.dto;

/**
 * Created by DK on 17/6/5.
 */
public class MachineModelDto {

    private String identifier;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return name;
    }
}
