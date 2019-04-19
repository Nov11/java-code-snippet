package pkg.batch_read;

import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.FindIterable;
import com.mongodb.async.client.MongoCollection;
import org.bson.Document;
import pkg.ConnectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.mongodb.client.model.Filters.in;

//batchsize which is larger than result size reduce getmore rt by one
public class AsyncReadWithBatching {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MongoCollection<Document> collection = ConnectUtils.asyncCollection;

        int batchSize = 500;
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            keys.add(String.valueOf(i));
        }
        List<Document> result = new ArrayList<>();
        FindIterable<Document> iterable = collection.find(in("_id", keys)).batchSize(batchSize);
//        System.out.println(iterable.batchSize());
        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        iterable.into(result, new SingleResultCallback<List<Document>>() {
            @Override
            public void onResult(List<Document> result, Throwable t) {
                System.out.println("done");
                completableFuture.complete(null);
            }
        });
        completableFuture.get();
        System.out.println(result.size());
    }
}
