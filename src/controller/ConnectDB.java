package controller;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class ConnectDB {
	private static MongoClient mongoClient = new MongoClient( "localhost" , 27017 );// connect to the database
	private static MongoDatabase mongoDatabase = mongoClient.getDatabase("commodity_category");  
    private static MongoCollection<Document> collection_commodity = mongoDatabase.getCollection("commodity_info");
    private static MongoCollection<Document> collection_user = mongoDatabase.getCollection("user_info");
    
    public static MongoCollection<Document> getCollection_Commodity() {
    		return collection_commodity;
    }
    
    public static MongoCollection<Document> getCollection_User() {
    		return collection_user;
    }
}
