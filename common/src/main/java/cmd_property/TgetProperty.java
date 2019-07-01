package cmd_property;

//idea -> vm options -Dxx=yy
public class TgetProperty {
    public static void main(String[] args) {
        String s = System.getProperty("abc");
        System.out.println(s);
    }
}
