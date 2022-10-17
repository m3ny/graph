package se.home.graph.repository;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.graph.Record;
import redis.clients.jedis.graph.ResultSet;
import redis.clients.jedis.graph.entities.GraphEntity;
import se.home.graph.model.Dog;
import se.home.graph.model.TestJson;
import se.home.graph.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@Repository
public class NodeRepository {

    private static final String DB_NAME = "dogs";

    @Autowired
    UnifiedJedis commands;

    public void testInsert(String dog){
        var query = String.format("CREATE (:dog {id: \"%s\",breed:'%s',legs:4})",UUID.randomUUID(),dog);
        System.out.println(query);
        commands.graphQuery(DB_NAME, query);
    }
    public void createUser(String firstName, String lastName){
        var t = String.format("CREATE (:user {id: \"%s\", firstName: \"%s\", lastName: \"%s\"})",UUID.randomUUID(),firstName,lastName);
        commands.graphQuery(DB_NAME,t);
    }
    public void createWalkingRelation(UUID userId, UUID dogId){
        var query = String.format("MATCH (a:user{id:'%s'}), (b:dog{id:'%s'}) CREATE (a)-[:WALKS]->(b)",userId,dogId);
        System.out.println(query);
        commands.graphQuery(DB_NAME,query);
    }

    public void createGenericRelation(UUID userId, UUID dogId, String relation){
        var query = String.format("MATCH (a:user{id:'%s'}), (b:dog{id:'%s'}) CREATE (a)-[:%s]->(b)",userId,dogId, relation.toUpperCase());
        System.out.println(query);
        commands.graphQuery(DB_NAME,query);
    }
    public void createDogFriendRelation(UUID dogId, UUID friendId){
        var query = String.format("MATCH (a:dog{id:'%s'}), (b:dog{id:'%s'}) CREATE (a)-[:FRIENDS]->(b)",dogId,friendId);
        System.out.println(query);
        commands.graphQuery(DB_NAME,query);
    }

    public void createPuppies(UUID dogId, int puppyCount){
        var query = String.format("MATCH (a:dog{id:'%s'}) return a",dogId);
        ResultSet rs = commands.graphQuery(DB_NAME,query);
        if(rs.size() == 0){
            throw new RuntimeException("No dog");
        }
        Record r = rs.iterator().next();
        var dog = (GraphEntity)r.getValue(0);
        createPupps(UUID.fromString(dog.getProperty("id").getValue().toString()), dog.getProperty("breed").getValue().toString(),puppyCount);

    }
    private void createPupps(UUID parent,String dogType, int puppyCount){
            Stream.iterate(0,n->n+1)
                    .limit(puppyCount)
                    .forEach(
                            i -> {
                                var id = UUID.randomUUID();
                                commands.graphQuery(DB_NAME, String.format("CREATE (:dog{id: \"%s\",breed: \"%s\",legs:4})",id,dogType));
                                addParentRelation(id,parent);
                            }
                    );
    }
    private void addParentRelation(UUID pupId, UUID parentId){
        var query = String.format("MATCH (a:dog{id:'%s'}), (b:dog{id:'%s'}) CREATE (a)-[:PARENT]->(b)",pupId,parentId);
        System.out.println(query);
        commands.graphQuery(DB_NAME,query);
    }
    public void testJson(TestJson testJson) {
        testJson.setId(UUID.randomUUID());
        var query = String.format("CREATE (:%s)",testJson.toString());
        System.out.println(query);
        commands.graphQuery(DB_NAME, query);

    }
    public List<Dog> getDogs(){
        List<Dog> dogs = new ArrayList<>();
        var query = "MATCH (n) where n:dog return n";
        ResultSet rs = commands.graphQuery(DB_NAME, query);
        rs.forEach(record -> dogs.add(createDog(record)));
        return dogs;
    }
    public List<User> getUsers(){
        List<User> dogs = new ArrayList<>();
        var query = "MATCH (n) where n:user return n";
        ResultSet rs = commands.graphQuery(DB_NAME, query);
        rs.forEach(record -> dogs.add(createUser(record)));
        return dogs;
    }
    public Dog createDog(Record r){
        GraphEntity t = r.getValue(0);
        return new Dog(UUID.fromString(t.getProperty("id").getValue().toString()),t.getProperty("breed").getValue().toString(),Integer.parseInt(t.getProperty("legs").getValue().toString()));
    }
    public User createUser(Record r){
        GraphEntity t = r.getValue(0);
        return new User(UUID.fromString(t.getProperty("id").getValue().toString()),t.getProperty("firstName").getValue().toString(),t.getProperty("lastName").getValue().toString());
    }


}
