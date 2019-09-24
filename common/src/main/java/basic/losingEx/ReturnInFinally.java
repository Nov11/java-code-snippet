package basic.losingEx;

//exception is not passed to caller but swallowed
public class ReturnInFinally {
    private static void foo() {
        try {
            throw new RuntimeException("in try");
        } finally {
            return;
        }
    }

    public static void main(String[] args) {
        foo();
    }
}
