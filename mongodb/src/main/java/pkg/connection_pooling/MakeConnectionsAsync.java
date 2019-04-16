package pkg.connection_pooling;

import com.mongodb.Block;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.*;
import org.bson.Document;

import java.util.concurrent.TimeUnit;

public class MakeConnectionsAsync {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(MakeConnectionsAsync.class);

    public static void main(String[] args) throws InterruptedException {
        MongoClient mongoClient = MongoClients.create(mongoClientSettings());
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("abc");

        Block<? super Document> block = System.out::println;
        //this doesn't look like the right way
        collection.find().forEach(block, new SingleResultCallback(){
            @Override
            public void onResult(Object result, Throwable t) {

            }
        });


        logger.info("10 find operation is done");

        Thread.sleep(100000000);
    }


    private static MongoClientSettings mongoClientSettings() {
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
