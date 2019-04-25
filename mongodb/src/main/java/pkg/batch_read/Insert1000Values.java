package pkg.batch_read;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import pkg.ConnectUtils;

import java.util.Date;

public class Insert1000Values {

    public static void main(String[] args) {

        MongoCollection<Document> collection = ConnectUtils.collection;

        for (int i = 0; i < 1000; i++) {

            Document document = new Document();
            document.put("_id", String.valueOf(i));
            document.put("name", i + "'s name");
            document.put("date", new Date());

            collection.insertOne(document);
        }

        System.out.println("done");
    }
}
