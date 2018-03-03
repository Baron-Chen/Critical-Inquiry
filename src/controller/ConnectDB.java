package controller;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ConnectDB {
	MongoClient mongoClient = new MongoClient( "localhost" , 27017 );// connect to the database
    MongoDatabase mongoDatabase = mongoClient.getDatabase("commodity_category");  
    MongoCollection<Document> collection_commodity = mongoDatabase.getCollection("commodity_info");
    MongoCollection<Document> collection_user = mongoDatabase.getCollection("user_info");
    
    public MongoCollection<Document> getCollection_Commodity() {
    		return collection_commodity;
    }
    
    public MongoCollection<Document> getCollection_User() {
    		return collection_user;
    }
}
