package se.home.graph.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import se.home.graph.model.Dog;

@Service
public class DogService {
    private final WebClient webClient;

    public DogService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://dog.ceo/api/breeds/image/random").build();
    }
    public Mono<String> getDog(){
        return webClient.get().retrieve().bodyToMono(Dog.class).map(this::getBreed);

    }
    private String getBreed(Dog d){
        var dogBreed = d.getMessage().replaceFirst("https:\\/\\/images.dog.ceo\\/breeds\\/","");
        int index=dogBreed.lastIndexOf('/');
        return dogBreed.substring(0,index);
    }
}
