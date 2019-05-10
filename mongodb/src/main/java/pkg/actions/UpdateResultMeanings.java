package pkg.actions;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.Collections;

import static com.mongodb.client.model.Updates.set;
import static pkg.ConnectUtils.collection;

public class UpdateResultMeanings {
    public static void main(String[] args) {
        MongoCollection<Document> mongoCollection = collection;
        Document insert = new Document();
        insert.put("_id", "1");
        insert.put("name", "n");
        collection.deleteOne(insert);
        UpdateOptions upsert = new UpdateOptions().upsert(true);
        UpdateOneModel<Document> updateOneModel = new UpdateOneModel<Document>(insert, set("name", "n"), upsert);

        BulkWriteResult result = collection.bulkWrite(Collections.singletonList(updateOneModel));
        System.out.println(result);
    }
}
