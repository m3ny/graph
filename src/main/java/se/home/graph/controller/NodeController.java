package se.home.graph.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import se.home.graph.model.*;
import se.home.graph.model.requests.DogFriend;
import se.home.graph.model.requests.Puppies;
import se.home.graph.model.requests.Req;
import se.home.graph.model.requests.ReqWithRelation;
import se.home.graph.repository.NodeRepository;
import se.home.graph.service.DogService;

import java.util.List;

@RestController
public class NodeController {
    @Autowired
    NodeRepository repository;

    @Autowired
    DogService service;

    @PostMapping("/dog")
    public Mono<String> getTest(){
        return service.getDog()
                        .flatMap(this::blockWrapper)
                                .thenReturn("added");

    }
    @PostMapping("/user")
    public Mono<Void> createUser(@RequestBody User user){
        return Mono.fromRunnable(() -> repository.createUser(user.getFirstName(), user.getLastName())).subscribeOn(Schedulers.boundedElastic()).then();
    }
    @GetMapping("/dogs")
    public Mono<List<Dog>> getDogs(){
        var tmp = repository.getDogs();
        return Mono.just(tmp);
    }
    @GetMapping("/users")
    public Mono<List<User>> getUsers(){
        var tmp = repository.getUsers();
        return Mono.just(tmp);
    }
    @PostMapping("/walks")
    public Mono<Void> walkDog(@RequestBody Req req){
        return Mono.fromRunnable(() -> repository.createWalkingRelation(req.getUserId(), req.getDogId())).subscribeOn(Schedulers.boundedElastic()).then();
    }
    @PostMapping("/custom")
    public Mono<Void> createGenericRelation(@RequestBody ReqWithRelation req){
        return Mono.fromRunnable(() -> repository.createGenericRelation(req.getUserId(), req.getDogId(), req.getRelation())).subscribeOn(Schedulers.boundedElastic()).then();
    }
    @PostMapping("/friend")
    public Mono<Void> createFriendRelation(@RequestBody DogFriend req){
        return Mono.fromRunnable(() -> repository.createDogFriendRelation(req.getDogId(), req.getFriendId())).subscribeOn(Schedulers.boundedElastic()).then();
    }
    @PostMapping("/puppies")
    public Mono<Void> addPuppies(@RequestBody Puppies puppies){
        return Mono.fromRunnable(() -> repository.createPuppies(puppies.getParent(), puppies.getPuppyCount())).subscribeOn(Schedulers.boundedElastic()).then();
    }
    @PostMapping("/test")
    public Mono<Void> test(@RequestBody TestJson testJson){
        return Mono.fromRunnable(() -> repository.testJson(testJson)).subscribeOn(Schedulers.boundedElastic()).then();
    }

    private Mono<Void> blockWrapper(String d){
        return Mono.fromRunnable(() -> repository.testInsert(d)).subscribeOn(Schedulers.boundedElastic()).then();
    }

}
