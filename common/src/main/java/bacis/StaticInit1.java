package bacis;

public class StaticInit1 {
    static {
        StaticInit2.hash.put("k", "v");
        System.out.println(StaticInit1.class.getSimpleName());
    }


}
