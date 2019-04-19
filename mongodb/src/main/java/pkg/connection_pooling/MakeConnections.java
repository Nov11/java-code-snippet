package pkg.connection_pooling;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;

import java.util.concurrent.TimeUnit;

public class MakeConnections {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MakeConnections.class);

    public static void main(String[] args) throws InterruptedException {


        MongoClient mongoClient = MongoClients.create(mongoClientSettings());
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("abc");
        for (int i = 0; i < 10; i++) {
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document d = cursor.next();
                    System.out.println(d);
                }
            }
        }

        logger.info("10 find operation is done");

        Thread.sleep(100000000);
    }


    public static MongoClientSettings mongoClientSettings() {
        return MongoClientSettings.builder()
                .applyToConnectionPoolSettings(builder -> builder
                        .maxSize(2)
                        .minSize(0)
                        .maxConnectionIdleTime(10, TimeUnit.SECONDS)
                        .maxWaitTime(0, TimeUnit.SECONDS)
                        .maxWaitQueueSize(100)
                )
                .applyToSocketSettings(builder -> builder.readTimeout(10, TimeUnit.MILLISECONDS))
                .build();
    }
}
