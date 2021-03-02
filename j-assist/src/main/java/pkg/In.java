package pkg;

public class In {
    static class A {
        static String sa = "a";

        public String get() {
            return sa;
        }
    }

    static class B extends A {
        static String sa = "b";

        public String get() {
            return sa;
        }
    }

    public static void main(String[] args) {
        B b = new B();
        System.out.println(b.get());
    }
}
