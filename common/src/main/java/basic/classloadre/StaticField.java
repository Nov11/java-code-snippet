package basic.classloadre;

public class StaticField {
    static String name = init();

    private static String init() {
        System.out.println("init called");
        return "name";
    }
}
