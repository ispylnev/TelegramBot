package database;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import java.util.ArrayList;
import java.util.logging.Level;



public class MongoDbWork {
    private Logger databaseLogger = LogManager.getLogger(MongoDbWork.class);
    private MongoClient connection;
    private MongoDatabase database;
    private MongoCollection <Document> collection;
    private BasicDBObject basicDBObject = new BasicDBObject();

   public MongoDbWork() throws MongoSocketException {
       java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
           connection = new MongoClient(new MongoClientURI("mongodb://admin:admin123@ds217002.mlab.com:17002/telebot"));
           database = connection.getDatabase("telebot");
           collection = database.getCollection("user");
       try {
           databaseLogger.info("connect Mongo database --->OK");
       } catch (MongoSocketException e){
           System.out.println("ssd");
       } catch (MongoException e) {
           databaseLogger.fatal("connect Error "+ e.getMessage());

       }
   }

   public  void addUser(String userName,String firstName, int userId ) {
       long found = collection.count(Document.parse("{userId : " + Integer.toString(userId) + "}"));
       if (found == 0) {
           System.out.println(found);
           Document document = new Document();
           document.append("userName", userName)
                   .append("firstName", firstName)
                   .append("userId", userId)
                   .append("check", "true");
           collection.insertOne(document);
           databaseLogger.info("Add user Ok");
//         connection.close();
       }
   }

    public <T extends String ,V> Document findFieldInDoc(T field,V value){
       try {
           return collection.find(new Document(field, value)).first();
       }catch (NullPointerException e){
           databaseLogger.error("error. Not found field");
           return null;
       }
   }



//обновляет существующй документ путем добавления продолжительности в виде секунд
   public void updateDate(int userId,String nameArray,long dateToLong){
     Document found = collection.find(new Document("userId",userId)).first();
//       Document found = findFieldInDoc("userId",userId);
     basicDBObject.put("$push",new BasicDBObject(nameArray,dateToLong));
     collection.updateOne(found,basicDBObject);

   }

   public Document queryDoc(int userId){

      return database.getCollection("user").find(new Document("userId",userId)).first();
   }

// показывает отработаное время
   public long queryWorkingHourse(Document document,String date){
     ArrayList<Long> arrayDate = (ArrayList<Long>)document.get(date);
       System.out.println(arrayDate);
     long sum = 0;
     for(int i = 0;i < arrayDate.size();i++){
         sum = sum + Long.parseLong(String.valueOf(arrayDate.get(i)));
     }
     return sum;
   }

   public <T> void updateDocument(int userId, T field){
       Document found = findFieldInDoc("userId",userId);
       Document document = new Document();
       document.put("$set", new Document("check",field));
       collection.updateOne(found,document);
   }
}

