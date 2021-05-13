package pkg.httpclient;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

//Jetty limit get uri length to 8K
//10K header will produce 414 URI too long response
public class GetRequest {
    private static final Logger logger = LoggerFactory.getLogger(GetRequest.class);

    private static String param(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + i % 26);
            builder.append(c);
        }
        return builder.toString();
    }

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10; i++) {

            String param = param(1024 * 10);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpHost httpHost = new HttpHost("localhost", 3456);
            HttpGet httpGet = new HttpGet("/?param=" + param);
//        HttpGet httpGet = new HttpGet("/ss?param1=12345:xx,67890:yy&param2=67890:zz,222:99&param3=h l");
            HttpResponse httpResponse = httpClient.execute(httpHost, httpGet);
            logger.info("resp : {}", httpResponse);
        }

    }
}
