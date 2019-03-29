package pkg.actions;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AllocateManyCollectionObjects {
    public static void main(String[] args) throws InterruptedException {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("testdb");
        List<MongoCollection<Document>> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(database.getCollection("c" + i));
        }

        CountDownLatch countDownLatch = new CountDownLatch(1);


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            countDownLatch.countDown();
            System.out.println("count down");
        }));

        countDownLatch.await();
        System.out.println("exit");
    }
}
