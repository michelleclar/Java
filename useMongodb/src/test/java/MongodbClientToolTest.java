import org.bson.Document;
import org.bson.types.ObjectId;
import org.carl.tool.MongodbClientTool;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

public class MongodbClientToolTest {
    @Test
    public void createCollection() {
        //don't create
        MongodbClientTool.getDatabase("db").getCollection("study");
        //or
        //sync create
        MongodbClientTool.getDatabase("db").createCollection("study");
    }

    @Test
    public void dropDatabase() {
        MongodbClientTool.getDatabase("db").drop();
    }

    @Test
    public void insertOneDoc() {
        MongodbClientTool.getDatabase("db").getCollection("study").insertOne(new Document()
                .append("_id", new ObjectId())
                .append("title", "Ski Bloopers")
                .append("genres", Arrays.asList("Documentary", "Comedy")));
    }
    @Test
    public void findOne(){
        Document doc = MongodbClientTool.getDatabase("db").getCollection("study").find(eq("title", "Ski Bloopers")).first();
        System.out.println(doc);
    }


}
