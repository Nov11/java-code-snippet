package pkg.aop;

public class Tool implements ToolInterface {
    public void foo(){
        System.out.println("foo");
    }

    @Override
    @wow
    public void bar() {
        System.out.println("bar");
    }
}
