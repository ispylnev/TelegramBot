package database;
import com.mongodb.*;
import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;


public class Connection {
   private boolean auth;
   private DBCollection table;
   private DB db;

   public void startMongo(){
//      MongoCredential mongoCredential = MongoCredential.createScramSha1Credential ("ispylnev","admin","admin123".toCharArray());
      MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:admin123@ds217002.mlab.com:17002/telebot"));
      MongoDatabase database = mongoClient.getDatabase("telebot");
//      MongoCollection<Document> collection = database.getCollection("user");
//      long found  = collection.count(Document.parse("{id :"+Integer.toString(1)+ "}"));




   }


}
