package basic.static_field_init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadAndInit {
    private static final Logger logger = LoggerFactory.getLogger(LoadAndInit.class);

    static class Item {
        public Item() {
            logger.info("item ctor");
        }
    }

    public static class A {
        static Item item = new Item();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String className = "basic.static_field_init.LoadAndInit$A";
        //shows : item ctor
//        Class c = Class.forName(className);

        //nothinbg
        Class c = LoadAndInit.class.getClassLoader().loadClass(className);
    }
}
