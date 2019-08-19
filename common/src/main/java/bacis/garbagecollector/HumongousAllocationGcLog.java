package bacis.garbagecollector;

import java.util.ArrayList;
import java.util.List;

//oom
public class HumongousAllocationGcLog {
    private static List<byte[]> holder = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            holder.add(new byte[1500 * 1024]);
        }
    }
}
