package pkg.connection_pooling;

import com.mongodb.client.*;
import org.bson.Document;

import static pkg.ConnectUtils.mongoClientSettings;

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


}
