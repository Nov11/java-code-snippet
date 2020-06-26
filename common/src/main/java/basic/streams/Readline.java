package basic.streams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Readline {
    private static final Logger logger = LoggerFactory.getLogger(Readline.class);

    public static void main(String[] args) throws IOException {
        byte[] bytes = new byte[5];
        bytes[0] = (byte) 155;
        bytes[1] = (byte) 199;
        bytes[2] = (byte) '\n';
        logger.info("bytes {}", bytes);

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(byteArrayInputStream, StandardCharsets.ISO_8859_1));
        String line = bufferedReader.readLine();
        System.out.println(line.getBytes().length);
        logger.info("bytes {}", line.getBytes());
    }
}
