package database;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;



public class MongoDbWork {

   private MongoClient connection;
   private MongoDatabase database;
   private MongoCollection <Document> collection;

   public MongoDbWork(){ //Todo оптимизация на пулл соединений или хотя-бы сделать закрытие после каждого запроса к бд
      connection = new MongoClient(new MongoClientURI("mongodb://admin:admin123@ds217002.mlab.com:17002/telebot"));
      database = connection.getDatabase("telebot");
      System.out.println("Connect MongoDB--->OK");//Todo log
   }

   public  void addUser(String userName,String firstName, int userId ){
      collection = database.getCollection("user");
      long found = collection.count(Document.parse("{userId : " + Integer.toString(userId) + "}"));
      if (found == 0){
         System.out.println(found);
         Document document = new Document();
         document.append("userName",userName)
                 .append("firstName",firstName)
                 .append("userId",userId);
         collection.insertOne(document);
         System.out.println("Add user ok"); //todo log
//         connection.close();
      }

   }

   public void updateDate(int userId,String nameArray,long dateToLong){
     collection =  database.getCollection("user");
     Document found = (Document) collection.find(new Document("userId",userId)).first();
     BasicDBObject basicDBObject = new BasicDBObject();
     basicDBObject.put("$push",new BasicDBObject(nameArray,dateToLong));
     collection.updateOne(found,basicDBObject);

   }

}
