package pkg.test_collection_maker;

import com.mongodb.client.MongoCollection;
import org.bson.BsonDateTime;
import org.bson.Document;

import java.util.Calendar;
import java.util.Date;

import static pkg.ConnectUtils.syncCollection;

public class InsertDocuments {
    public static void main(String[] args) {
//        insert("test", "lsmindex");
        insert("test", "testc");
//        insert("test", "ttlc");

    }

    private static void insert(String db, String collection) {
        MongoCollection<Document> ret = syncCollection(db, collection);
        for (int i = 1; i < 100; i++) {
            ret.insertOne(makeDoc(String.valueOf(i), "doc" + i, 10));
        }
        System.out.println("done");
    }

    //first try:pass time with date 1~99
    //second try: pass time with bson date 100 ~ 199
    private static Document makeDoc(String id, String name, int secondsToExpire) {
        Document document = new Document();

        document.put("_id", id);
        document.put("name", name);
        document.put("expire", secondsInFuture(secondsToExpire));
//        document.put("expire", secondsInFutureBson(secondsToExpire));

        return document;
    }

    public static Date secondsInFuture(int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    private static BsonDateTime secondsInFutureBson(int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, seconds);
        Date date = calendar.getTime();
        return new BsonDateTime(date.getTime());
    }
}
