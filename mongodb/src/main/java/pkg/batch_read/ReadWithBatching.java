package pkg.batch_read;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import pkg.ConnectUtils;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.in;

public class ReadWithBatching {
    public static void main(String[] args) {
        MongoCollection<Document> collection = ConnectUtils.collection;

        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 600; i++) {
            keys.add(String.valueOf(i));
        }
        List<Document> result = new ArrayList<>();
        FindIterable<Document> iterable = collection.find(in("_id", keys));//.batchSize(400);
        iterable.into(result);
        System.out.println(result.size());
    }
}
