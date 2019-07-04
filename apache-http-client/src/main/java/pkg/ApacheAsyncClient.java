package pkg;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ApacheAsyncClient {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ApacheAsyncClient.class);
    private CloseableHttpAsyncClient httpAsyncClient;
    private HttpHost httpHost = new HttpHost("localhost", 3456);
    private HttpGet httpGet = new HttpGet("/test");

    public ApacheAsyncClient() {
        httpAsyncClient = HttpAsyncClients.custom().build();
        httpAsyncClient.start();
    }

    static class Callback extends CompletableFuture<String> implements FutureCallback<HttpResponse> {

        @Override
        public void completed(HttpResponse response) {
            HttpEntity httpEntity = response.getEntity();
            String s = "default";
            try {
                s = EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    EntityUtils.consume(httpEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            this.complete(s);
        }

        @Override
        public void failed(Exception ex) {
            this.completeExceptionally(ex);
        }

        @Override
        public void cancelled() {
            this.completeExceptionally(new RuntimeException("cancelled"));
        }
    }

    public CompletableFuture<String> makeRequest() throws ExecutionException, InterruptedException {
        Callback callback = new Callback();
        Future<HttpResponse> ret = httpAsyncClient.execute(httpHost, httpGet, callback);
        return callback;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ApacheAsyncClient apacheAsyncClient = new ApacheAsyncClient();
        {
            String result = apacheAsyncClient.makeRequest().get();
            logger.info("ret : {}", result);
        }
        logger.info("=============");
//        {
//            String result = apacheAsyncClient.makeRequest().get();
//            logger.info("ret : {}", result);
//        }
        Thread.sleep(60000);
        logger.info("exiting");
        System.exit(0);
    }
}
