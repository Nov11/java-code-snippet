package pkg.bulk_write;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;

public class BulkWriteMulitpleUpdate {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = database.getCollection("abc");
        UpdateOptions updateOptions = new UpdateOptions().upsert(true);
        List<UpdateOneModel<Document>> updateOneModels = new ArrayList<>();
        updateOneModels.add(new UpdateOneModel<>(
                eq("_id", 11),
                combine(set("name", "jack1")
                        , inc("cnt", 1)),
                updateOptions
        ));
        updateOneModels.add(new UpdateOneModel<>(
                eq("_id", 11),
                combine(set("address", "street")
                        , set("name", "jack2")
                        , inc("cnt", 1)),
                updateOptions
        ));
        BulkWriteResult result = collection.bulkWrite(updateOneModels, new BulkWriteOptions().ordered(false));
        System.out.println(result);
    }
}
