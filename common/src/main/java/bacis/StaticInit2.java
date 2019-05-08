package bacis;

import java.util.HashMap;
import java.util.Map;

public class StaticInit2 {
    static Map<String, String> hash
            = new HashMap<>();
    static {
        hash.put("k", "v2");
        System.out.println(StaticInit2.class.getSimpleName());
    }

    static String get(){
        return hash.get("k");
    }
}
