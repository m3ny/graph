package se.home.graph.model.requests;

import java.util.UUID;

public class ReqWithRelation {
    UUID userId;
    UUID dogId;
    String relation;

    public ReqWithRelation(UUID userId, UUID dogId, String relation) {
        this.userId = userId;
        this.dogId = dogId;
        this.relation = relation;
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

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
