package org.carl.monogdb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

@ApplicationScoped
public class MongodbService {
  @Inject
  MongoClient mongoClient;

  public List<Fruit> list() {
    List<Fruit> list = new ArrayList<>();
    MongoCursor<Document> cursor = getCollection().find().iterator();

    try {
      while (cursor.hasNext()) {
        Document document = cursor.next();
        Fruit fruit = new Fruit();
        fruit.setName(document.getString("name"));
        fruit.setDescription(document.getString("description"));
        list.add(fruit);
      }
    } finally {
      cursor.close();
    }
    return list;
  }

  public void add(Fruit fruit) {
    Document document = new Document().append("name", fruit.getName()).append("description",
        fruit.getDescription());
    getCollection().insertOne(document);
  }

  private MongoCollection getCollection() {
    return mongoClient.getDatabase("fruit").getCollection("fruit");
  }
}
