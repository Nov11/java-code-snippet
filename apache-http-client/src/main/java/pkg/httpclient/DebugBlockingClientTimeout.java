package pkg.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DebugBlockingClientTimeout {
    private static final Logger logger = LoggerFactory.getLogger(DebugBlockingClientTimeout.class);

    public static void main(String[] args) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(100)
                .setConnectTimeout(100)
                .setConnectionRequestTimeout(100)
                .build();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setMaxConnPerRoute(200)
                .setMaxConnTotal(400)
                .disableCookieManagement()
                .disableRedirectHandling()
                .setDefaultRequestConfig(requestConfig);
        HttpClient httpClient = httpClientBuilder.build();
        HttpHost target = new HttpHost("localhost", 3456);
        HttpGet httpGet = new HttpGet("/ss");
        HttpResponse response = httpClient.execute(target, httpGet);

        logger.info("response : {}", response);
    }
}
