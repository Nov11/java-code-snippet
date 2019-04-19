package pkg;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import static pkg.connection_pooling.MakeConnections.mongoClientSettings;

public class ConnectUtils {

    public static MongoClient mongoClient = MongoClients.create(mongoClientSettings());
    public static MongoCollection<Document> collection = mongoClient.getDatabase("test")
            .getCollection("testc");

    public static com.mongodb.async.client.MongoClient asyncClient = com.mongodb.async.client.MongoClients.create(mongoClientSettings());
    public static com.mongodb.async.client.MongoCollection<Document> asyncCollection = asyncClient.getDatabase("test")
            .getCollection("testc");
}
