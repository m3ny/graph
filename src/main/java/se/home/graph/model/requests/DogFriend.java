package se.home.graph.model.requests;

import java.util.UUID;

public class DogFriend {
    UUID dogId;
    UUID friendId;

    public DogFriend(UUID dogId, UUID friendId) {
        this.dogId = dogId;
        this.friendId = friendId;
    }

    public UUID getDogId() {
        return dogId;
    }

    public void setDogId(UUID dogId) {
        this.dogId = dogId;
    }

    public UUID getFriendId() {
        return friendId;
    }

    public void setFriendId(UUID friendId) {
        this.friendId = friendId;
    }
}
