import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;



public class TestCase {
private MongoDatabase database;
private   MongoCollection <Document> mongoCollection;

    @Test
    public void startMongo() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:admin123@ds217002.mlab.com:17002/telebot"));
        database =  mongoClient.getDatabase("telebot");
        mongoCollection = database.getCollection("user");
        System.out.println("Database Connected");
        Document document = new Document();
        document.append("userName","userName")
                .append("firstName","firstName")
                .append("userId","userId")
                .append("data",new Document("data","data"));
        mongoCollection.insertOne(document);

    }
    @Test
    public void founAndReplaceObject (){
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:admin123@ds217002.mlab.com:17002/telebot"));
        database =  mongoClient.getDatabase("telebot");
        mongoCollection = database.getCollection("user");
        Document found = (Document) mongoCollection.find(new BasicDBObject("data",new BasicDBObject("data","data"))).first();
        if (found!=null) System.out.println("ok");
                Document document = new Document();
        document.put("$set",new Document("data", new BasicDBObject("data111","data311")) );
        mongoCollection.updateOne(found,document);
        System.out.println(found);

    }
    @Test
    public void findAndReplaceArray(){
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:admin123@ds217002.mlab.com:17002/telebot"));
        database =  mongoClient.getDatabase("telebot");
        mongoCollection = database.getCollection("user");
        Document found = (Document) mongoCollection.find(new Document("userName","userName")).first();
        if (found!=null) System.out.println("Ok");
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("$push",new BasicDBObject("DATA","DATASUUM1"));
        mongoCollection.updateOne(found, basicDBObject);
    }
}
