package se.home.graph.model.requests;

import java.util.UUID;

public class Req {
    UUID userId;
    UUID dogId;

    public Req(UUID userId, UUID dogId) {
        this.userId = userId;
        this.dogId = dogId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getDogId() {
        return dogId;
    }

    public void setDogId(UUID dogId) {
        this.dogId = dogId;
    }
}
