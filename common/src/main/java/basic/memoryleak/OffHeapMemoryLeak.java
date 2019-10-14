package basic.memoryleak;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class OffHeapMemoryLeak {
    private static final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    private static List<ByteBuffer> list = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("press to allocate");
            String ret = scanner.next();
            if (ret.equals("q")) {
                break;
            }
            ByteBuffer directBuffer = ByteBuffer.allocateDirect(1024 * 1024);
            for (int i = 0; i < 1024 * 1024; i++) {
                directBuffer.put((byte) i);
            }
            list.add(directBuffer);
        }
    }
}
