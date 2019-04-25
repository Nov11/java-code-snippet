package pkg.actions;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import pkg.ConnectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.mongodb.client.model.Filters.lt;

//it's working if not concerning myself with UTC / time zone but using local time.
public class DeleteByDate {
    public static void main(String[] args) throws ParseException {
        MongoCollection<Document> collection = ConnectUtils.collection;

        String d = "2019-04-22 10:57:34";
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);

        System.out.println(date);

        DeleteResult deleteResult = collection.deleteMany(lt("date", date));
        System.out.println(deleteResult);
    }
}
