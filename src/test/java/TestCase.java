import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BSON;
import org.bson.Document;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;


public class TestCase {
private MongoDatabase database;
private   MongoCollection <Document> mongoCollection;

    @Test
    public void startMongo() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:admin123@ds217002.mlab.com:17002/telebot"));
        database =  mongoClient.getDatabase("telebot");
        mongoCollection = database.getCollection("user");
//        assertTrue(database.getName().contains("telebott"));
        System.out.println("Database Connected");

        Document document = new Document();

//        Document found = mongoCollection.find(new Document("name","tests")).first();
//        System.out.println("ok");
//        found.append("date",new Document("fff","123"));
//       BSON updateVal = new Document()
//        System.out.println(found);

        document.append("Sex","male");
        document.append("Race","Afro");
        document.append("date",new Document("start","end"));
        mongoCollection.insertOne(document);

    }
    @Test
    public void creat (){
        Document document = new Document("test", "tests");
        document.append("Sex","male");
        document.append("Race","Afro");
    }
}
