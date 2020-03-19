package pkg.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ConnectionRetry3Times {
    public static void main(String[] args) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom().

                setConnectTimeout(10 * 1000).
                setConnectionRequestTimeout(10 * 1000).
                setSocketTimeout(10 * 1000).
                build();
        HttpClient httpClient = HttpClientBuilder.create().
                setRetryHandler((exception, executionCount, context) -> executionCount < 3).
                setDefaultRequestConfig(requestConfig).
                build();
//        HttpClient httpClient = HttpClients.custom()
//                .build();

        HttpResponse response = httpClient.execute(new HttpHost("localhost", 3456), new HttpGet("/test"));
        String result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
        System.out.println(result);
    }
}
