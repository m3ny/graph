package se.home.graph.model;

import java.util.UUID;

public class Dog {

    private UUID id;
    private String message;
    private int legs;

    public Dog(UUID id, String message, int legs) {
        this.id = id;
        this.message = message;
        this.legs = legs;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }
}
