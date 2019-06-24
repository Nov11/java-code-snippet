package bacis.tolowercase;

public class ToLowerCase {
    public static void main(String[] args) {

        String s = "ABCD";
        String lower = s.toLowerCase();
        String lower2 = s.toLowerCase();
        System.out.println(lower == lower2);
    }
}
