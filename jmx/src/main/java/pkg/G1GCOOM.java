package pkg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class G1GCOOM {
    private static final Logger logger = LoggerFactory.getLogger(G1GCOOM.class);

    public static void main(String[] args) throws InterruptedException {
        FullGCCountPolling.monitorFullGC();
        Scanner scanner = new Scanner(System.in);
        scanner.next();
        List<byte[]> list = new ArrayList<>();
        for (int i = 0; i < 1024; i++) {
            list.add(AllocationUtils.oneMega());
            if (i >= 46) {
                Thread.sleep(2000);
            }
            logger.info("{}", i);
        }
    }
}
