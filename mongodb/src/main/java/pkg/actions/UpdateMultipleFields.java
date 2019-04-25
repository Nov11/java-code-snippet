package pkg.actions;

import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Updates.inc;
import static pkg.ConnectUtils.collection;

public class UpdateMultipleFields {
    public static void main(String[] args) {
        MongoCollection<Document> mongoCollection = collection;
//        mongoCollection.updateMany(new BsonDocument(), inc("element", 1));
        mongoCollection.updateMany(new BsonDocument(), and(inc("element", 1), inc("element2", 19)));
    }
}
