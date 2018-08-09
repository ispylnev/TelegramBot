import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TestCase {

    @Test
    public void startMongo() {
//      MongoCredential mongoCredential = MongoCredential.createScramSha1Credential ("ispylnev","admin","admin123".toCharArray());
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://admin:admin123@ds217002.mlab.com:17002/telebot"));
        MongoDatabase database = mongoClient.getDatabase("telebot");
        assertTrue(database.getName().contains("telebot"));
    }
}
