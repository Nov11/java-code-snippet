package pkg;

import com.example.tutorial.Outer;

/**
 * enum type's default value is the first (smallest) enum value.
 */
public class DefaultEnum {
    public static void main(String[] args) {
        Outer.Request2 request2 = Outer.Request2.newBuilder().build();
        System.out.println(request2.getColor());
    }
}
