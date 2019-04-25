package pkg.actions;

import com.mongodb.client.MongoCollection;
import org.bson.BsonDocument;
import org.bson.Document;

import static com.mongodb.client.model.Updates.unset;
import static pkg.ConnectUtils.collection;

public class UpdateDeleteFields {
    public static void main(String[] args) {
        MongoCollection<Document> mongoCollection = collection;
//        mongoCollection.updateMany(new BsonDocument(), inc("element", 1));
        mongoCollection.updateMany(new BsonDocument(), unset("element"));
    }
}
