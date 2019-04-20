package pkg;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.connection.ClusterType;
import org.bson.Document;

import java.util.Collections;
import java.util.concurrent.TimeUnit;


public class ConnectUtils {

    public static MongoClient mongoClient = MongoClients.create(mongoClientSettings());
    public static MongoCollection<Document> collection = mongoClient.getDatabase("test")
            .getCollection("testc");

    public static com.mongodb.async.client.MongoClient asyncClient = com.mongodb.async.client.MongoClients.create(mongoClientSettings());
    public static com.mongodb.async.client.MongoCollection<Document> asyncCollection = asyncClient.getDatabase("test")
            .getCollection("testc");

    public static MongoCollection<Document> syncCollection(String dbName, String collectionName) {
        return mongoClient.getDatabase(dbName).getCollection(collectionName);
    }


    public static MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder()
                .applicationName("localhost-tester")
                .applyToClusterSettings(builder -> builder
                        .hosts(Collections.singletonList(new ServerAddress("localhost")))
                        .requiredClusterType(ClusterType.STANDALONE))
                .applyToConnectionPoolSettings(builder -> builder
                        .maxSize(66)
                        .minSize(0)
                        .maxConnectionIdleTime(10, TimeUnit.SECONDS)
                        .maxWaitTime(0, TimeUnit.SECONDS)
                        .maxWaitQueueSize(100)
                )
                .applyToSocketSettings(builder -> builder
                        .readTimeout(10, TimeUnit.MILLISECONDS)
                        .connectTimeout(100, TimeUnit.MILLISECONDS))
                .build();
    }
}
