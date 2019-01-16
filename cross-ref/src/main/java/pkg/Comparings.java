package pkg;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Comparings {
    public static void main(String[] args) {
        Map<String, String> hash = new HashMap<>();
        //https://stackoverflow.com/questions/46904399/comparator-comparing-for-map-entry-in-java-8
//        List<Map.Entry<String, String>> ret = hash.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey).reversed()).collect(Collectors.toList());
        List<Map.Entry<String, String>> ret = hash.entrySet().stream().sorted(Comparator.comparing(Map.Entry<String, String>::getKey).reversed()).collect(Collectors.toList());
    }
}
