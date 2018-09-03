package database;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import java.util.ArrayList;



public class MongoDbWork {
    final Logger databaseLogger = LogManager.getLogger(MongoDbWork.class);
    private MongoClient connection;
    private MongoDatabase database;
    private MongoCollection <Document> collection;

    BasicDBObject basicDBObject = new BasicDBObject();


   public MongoDbWork() { //Todo оптимизация на пулл соединений или хотя-бы сделать закрытие после каждого запроса к бд
//       java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
           connection = new MongoClient(new MongoClientURI("mongodb://admin:admin123@ds217002.mlab.com:17002/telebot"));
           database = connection.getDatabase("telebot");
       try {
           databaseLogger.info("connect Mongo database --->OK");
       }catch (MongoException e) {
           databaseLogger.fatal("connect Error "+ e.getMessage());
       }
   }

   public  void addUser(String userName,String firstName, int userId ) {

       collection = database.getCollection("user");
       long found = collection.count(Document.parse("{userId : " + Integer.toString(userId) + "}"));
       if (found == 0) {
           System.out.println(found);
           Document document = new Document();
           document.append("userName", userName)
                   .append("firstName", firstName)
                   .append("userId", userId)
                   .append("check", "null");
           collection.insertOne(document);
           databaseLogger.info("Add user Ok");
//         connection.close();
       }
   }

    public <T extends String ,V> Document findFieldInDoc(T field,V value){
        return collection.find(new Document(field,value)).first();
   }

   public <T> T getFieldInDoc(String fieldName){
       return (T) i ;

   }
//обновляет существующй документ путем добавления продолжительности в виде секунд
   public void updateDate(int userId,String nameArray,long dateToLong){
     collection =  database.getCollection("user");
//     Document found = collection.find(new Document("userId",userId)).first();
       Document found = findFieldInDoc("userId",userId);
     basicDBObject.put("$push",new BasicDBObject(nameArray,dateToLong));
     collection.updateOne(found,basicDBObject);

   }

   public Document queryDoc(int userId){

      return database.getCollection("user").find(new Document("userId",userId)).first();
   }

   public long queryWorkingHourse(Document document,String date){
     ArrayList<Long> arrayDate = (ArrayList<Long>)document.get(date);
     long sum = 0;
     for(int i = 0;i < arrayDate.size();i++){
         sum = sum + arrayDate.get(i);
     }
     return sum;
   }

   public <T> void updateDocument(int userId, T field){
       collection = database.getCollection("user");
       Document found = findFieldInDoc("userId",userId);
       basicDBObject.put("$set",new BasicDBObject("check",field));
       collection.updateOne(found,basicDBObject);
   }
}
