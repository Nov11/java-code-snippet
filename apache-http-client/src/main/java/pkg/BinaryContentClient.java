package pkg;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pkg.util.BytesCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class BinaryContentClient {
    private static final Logger logger = LoggerFactory.getLogger(BinaryContentClient.class);
    private CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClientBuilder.create().build();

    public BinaryContentClient() {
        httpAsyncClient.start();
    }

    public void sendAndReceive() {
        HttpPost httpPost = new HttpPost("/test");
//        httpPost.setHeader(HttpHeader.CONTENT_TYPE.name(), "application/octet-stream");
        HttpEntity httpEntity = new ByteArrayEntity(new byte[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, ContentType.APPLICATION_OCTET_STREAM);
        httpPost.setEntity(httpEntity);
        HttpHost httpHost = new HttpHost("localhost", 1026);
        BytesCallback callback = new BytesCallback();
        Future<HttpResponse> future = httpAsyncClient.execute(httpHost, httpPost, callback);
        try {
            byte[] bytes = callback.get();
            logger.info("resp begin:");
            for (int i = 0; i < bytes.length; i++) {
                logger.info("{} : {}", i, bytes[i]);
            }
            logger.info("resp end");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BinaryContentClient client = new BinaryContentClient();
        client.sendAndReceive();
    }
}
