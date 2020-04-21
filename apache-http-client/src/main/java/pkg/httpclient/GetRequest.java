package pkg.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GetRequest {
    private static final Logger logger = LoggerFactory.getLogger(GetRequest.class);

    public static void main(String[] args) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpHost httpHost = new HttpHost("localhost", 8090);
        HttpGet httpGet = new HttpGet("/ss?param1=12345:xx,67890:yy&param2=67890:zz,222:99&param3=h l");
        HttpResponse httpResponse = httpClient.execute(httpHost, httpGet);
        logger.info("resp : {}", httpResponse);
    }
}
