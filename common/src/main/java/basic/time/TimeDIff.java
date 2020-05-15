package basic.time;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeDIff {
    public static void main(String[] args) {
        LocalDateTime from = LocalDateTime.of(2020, 4, 26, 17, 30);
        LocalDateTime to = LocalDateTime.of(2020, 4, 26, 18, 0);
        System.out.println(Duration.between(from, to).toMinutes());
        LocalDateTime from2 = LocalDateTime.of(2020, 4, 26, 17, 30, 30);
        System.out.println(Duration.between(from2, to).toMinutes());

        System.out.println(from);
    }
}
