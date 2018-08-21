import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import java.time.Duration;
import java.util.ArrayList;
public class TestCase {
    private MongoDatabase database;
    private MongoCollection<Document> mongoCollection;



    @Before
    public void conncet() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:admin123@ds217002.mlab.com:17002/telebot"));
        database = mongoClient.getDatabase("telebot");
        mongoCollection = database.getCollection("user");
    }

    @Test
    public void startMongo() {
        System.out.println("Database Connected");
        Document document = new Document();
        document.append("userName", "userName")
                .append("firstName", "firstName")
                .append("userId", "userId")
                .append("data", new Document("data", "data"));
        mongoCollection.insertOne(document);

    }

    @Test
    public void founAndReplaceObject() {
        Document found = (Document) mongoCollection.find(new BasicDBObject("data", new BasicDBObject("data", "data"))).first();
        if (found != null) System.out.println("ok");
        Document document = new Document();
        document.put("$set", new Document("data", new BasicDBObject("data111", "data311")));
        mongoCollection.updateOne(found, document);
        System.out.println(found);

    }

    @Test
    public void findAndReplaceArray() {
        Document found = (Document) mongoCollection.find(new Document("userName", "userName")).first();
        if (found != null) System.out.println("Ok");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("$push", new BasicDBObject("DATA", "DATASUUM1"));
        mongoCollection.updateOne(found, basicDBObject);
    }

    @Test
    public void sumDuration() {
        Document found = (Document) mongoCollection.find(new Document("userName", "ispylnev")).first();
        ArrayList<Long> a = (ArrayList<Long>) found.get("Data");
        long b = a.get(0)+a.get(1)+a.get(2);
        Duration duration;
        duration.get(b);
        duration.
        System.out.println(duration);
        System.out.println(duration.toMinutes()%60);


//                    duration.toHours() % 24,
//                    duration.toMinutes() % 60,
//                    duration.toMillis() % 10););


//        Duration duration1;
//        Duration duration2;
//        for (int i =0; i<a.size();i++){
//           Duration af= a.get(i).plus(a.get(i++));
//            Duration sum = duration1.plus(a.get(i++));
//            System.out.println(sum.toHours());
//            System.out.println(a.get(0).toSeconds()) ;

//    }
    }
}


//    @After
//    public void deletCol(){
//        Document found =
//        mongoCollection.deleteOne()
//    }



