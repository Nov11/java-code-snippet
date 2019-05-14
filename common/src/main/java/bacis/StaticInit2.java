package bacis;

import java.util.HashMap;
import java.util.Map;

public class StaticInit2 {
    //this won't load StaticInit1. no matter staticInit1 create itself or not
//    static StaticInit1 staticInit1;
    //this produces NPE, as hash is create in the next line
//    static StaticInit1 staticInit1 = StaticInit1.init1;
    static Map<String, String> hash = new HashMap<>();
    static {

        hash.put("k", "v2");
        System.out.println(StaticInit2.class.getSimpleName());
    }

    static String get(){
        return hash.get("k");
    }
}
