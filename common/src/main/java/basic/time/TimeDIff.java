package basic.time;

import org.slf4j.Logger;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.slf4j.LoggerFactory.getLogger;

public class TimeDIff {
    private static final Logger logger = getLogger(TimeDIff.class);

    public static void main(String[] args) {
        LocalDateTime d1 = LocalDateTime.now();
        logger.info("{}", d1);
        Instant instant = d1.atZone(ZoneId.systemDefault()).toInstant();
        LocalDateTime ofInstant = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        logger.info("ofInstant : {}", ofInstant);

        long epochSecond = d1.atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        logger.info("epochSecond : {} sys mi : {}", epochSecond, System.currentTimeMillis() / 1000);
    }

    public static void foo() {
        LocalDateTime from = LocalDateTime.of(2020, 4, 26, 17, 30);
        LocalDateTime to = LocalDateTime.of(2020, 4, 26, 18, 0);
        System.out.println(Duration.between(from, to).toMinutes());
        LocalDateTime from2 = LocalDateTime.of(2020, 4, 26, 17, 30, 30);
        System.out.println(Duration.between(from2, to).toMinutes());

        System.out.println(from);
    }
}
