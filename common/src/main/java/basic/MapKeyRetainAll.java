package basic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapKeyRetainAll {
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("a2", 13);
        map.put("a3", 14);
        map.put("a4", 15);
        Set<String> set = new HashSet<>();
        set.add("a2");
        set.add("a3");

        map.keySet().retainAll(set);

        System.out.println(map.size());
    }
}
