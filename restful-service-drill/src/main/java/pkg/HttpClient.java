package pkg;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class HttpClient {
    private static final Logger logger = getLogger(HttpClient.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultSocketConfig(socketConfig)
                .setMaxConnTotal(100)
                .setMaxConnPerRoute(100)
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setStaleConnectionCheckEnabled(true)
                        .build())
                .build();

        for (int i = 0; i < 10; i++) {
            HttpGet request = new HttpGet("http://localhost:8090");

            httpClient.execute(request, response -> {
                String content = EntityUtils.toString(response.getEntity());
                logger.info("resp : {}", content);
                return content;
            });

            Thread.sleep(10 * 1000);
        }
    }
}
