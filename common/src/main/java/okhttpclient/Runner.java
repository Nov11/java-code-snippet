package okhttpclient;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Scanner;

public class Runner {
    private static final Logger logger = LoggerFactory.getLogger(Runner.class);
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://localhost:3456/test").build();

        while (true) {
            if (!scanner.hasNext()) {
                return;
            }
            String input = scanner.nextLine();
            Response response = httpClient.newCall(request).execute();
            String s = response.body().string();
            logger.info("response : {}", s);
        }

    }
}
