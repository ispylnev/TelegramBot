package database;
import com.mongodb.*;
import com.mongodb.client.ListDatabasesIterable;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;


public class MongoDbWork {

   private MongoClient connection;
   private MongoDatabase database;
   MongoCollection <Document> collection;

  public void MongoDbWork(){ //Todo оптимизация на пулл соединений или хотя-бы сделать закрытие после каждого запроса к бд
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

   public void updateDate(int userId){
     collection =  database.getCollection("user");
     Document found = (Document) collection.find(new Document("userId",userId));
     Bson updateValue = new Document("beginTime","13").append("endTime","21");
     Bson updateOperation = new Document("$push",updateValue);
//     collection.updateOne(u)



   }

}
