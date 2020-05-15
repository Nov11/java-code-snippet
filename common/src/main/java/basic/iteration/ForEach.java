package basic.iteration;


import java.util.HashMap;
import java.util.Map;

public class ForEach {
    public static void main(String[] args) {
        Map<String, String> hash = new HashMap<>();
        hash.put("1", "a");
        hash.put("2", "b");
        hash.put("3", "c");
        hash.put("4", "d");
        for (Map.Entry<String, String> entry : hash.entrySet()) {
            if (entry.getKey().equals("1")) {
                hash.remove("1");
            }
        }
    }
}
