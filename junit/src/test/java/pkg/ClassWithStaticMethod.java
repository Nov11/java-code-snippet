package pkg;

public class ClassWithStaticMethod {
    private ClassWithStaticMethod() {

    }

    public static RetValue foo() {
        return new RetValue();
    }

    public static class InnerPublic {

    }

    public static class RetValue {
        public String bar() {
            return "bar";
        }
    }
}
