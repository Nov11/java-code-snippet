package basic.containers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.WeakHashMap;

/**
 * K in put(K,V) is used as parameter to construct Entry object which extends WeakReference.
 * When last strong reference to K is gone, the Entry holds K is alleged to be collected.
 */
public class WeakRef {
    private static final Logger logger = LoggerFactory.getLogger(WeakRef.class);

    public static void main(String[] args) throws InterruptedException {
        entryIsCollected();
    }

    private static void entryIsCollected() throws InterruptedException {
        WeakHashMap<Item, String> map = new WeakHashMap<>();
        Item key = new Item();
        map.put(key, "value");
        key = null;
        logger.info("before gc : map size : {} value : {}", map.size(), map.entrySet().iterator().next());

        System.gc();

        Thread.sleep(1000);
        logger.info("after gc : map size : {} value : {}", map.size(), map.keySet().size());
    }

    private static class Item {
        int i = 0;

        @Override
        protected void finalize() throws Throwable {
            logger.info("Item's finalize is called");
            super.finalize();
        }
    }
}
