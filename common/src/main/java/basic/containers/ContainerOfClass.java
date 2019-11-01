package basic.containers;

import java.util.ArrayList;
import java.util.List;

public class ContainerOfClass {
    public static void main(String[] args) {
        List<Class> list = new ArrayList<>();
        list.add(Integer.class);
        System.out.println(list.contains(Integer.class));
    }
}
