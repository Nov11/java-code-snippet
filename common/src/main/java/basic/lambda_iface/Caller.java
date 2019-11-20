package basic.lambda_iface;

public class Caller {
    public void fun(SomeIface<String> iface) {
        iface.work("s");
    }

    public static void main(String[] args) {
        Caller caller = new Caller();
        caller.fun(s -> s);
    }
}
