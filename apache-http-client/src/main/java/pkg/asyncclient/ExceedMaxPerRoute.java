package pkg.asyncclient;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.slf4j.LoggerFactory.getLogger;

public class ExceedMaxPerRoute {
    private static final Logger logger = getLogger(ExceedMaxPerRoute.class);

    private static void doRequest(CloseableHttpAsyncClient asyncClient) {
        Future<HttpResponse> ret = asyncClient.execute(new HttpHost("localhost", 3456), new HttpGet("/test"), new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {
                logger.info("CALLBACK response : {}", httpResponse);
            }

            @Override
            public void failed(Exception e) {
                logger.error("failed : ", e);
            }

            @Override
            public void cancelled() {
                logger.error("cancelled ");
            }
        });
        logger.info("request!");
    }

    public static void main(String[] args) throws InterruptedException, IOException, ExecutionException {
        CloseableHttpAsyncClient asyncClient = HttpAsyncClientBuilder.create().setMaxConnPerRoute(3).setDefaultRequestConfig(RequestConfig.custom().setSocketTimeout(20 * 1000).build()).build();
        asyncClient.start();

        for (int i = 0; i < 4; i++) {
            doRequest(asyncClient);
        }


        //close
        Thread.sleep(60 * 1000);
        asyncClient.close();
    }
}
