package se.home.graph.model.requests;

import java.util.UUID;

public class Puppies {
    UUID parent;
    int puppyCount;

    public Puppies(UUID parent, int puppyCount) {
        this.parent = parent;
        this.puppyCount = puppyCount;
    }

    public UUID getParent() {
        return parent;
    }

    public void setParent(UUID parent) {
        this.parent = parent;
    }

    public int getPuppyCount() {
        return puppyCount;
    }

    public void setPuppyCount(int puppyCount) {
        this.puppyCount = puppyCount;
    }
}
