package baeldung.netty.common;

public class Response {
    private int returnValue;

    public int getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(int returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public String toString() {
        return "Response{" +
                "returnValue=" + returnValue +
                '}';
    }
}
