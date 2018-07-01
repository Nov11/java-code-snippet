package java8inaction.chapter11_completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by c6s on 18-7-1
 */
public class ServiceDrive {
    public static void main(String[] args) {
        Shop shop = new Shop();
        String productName = "iphone2048s";
        long start = System.currentTimeMillis();
//        System.out.println("starts at:" + start);
        CompletableFuture<Double> ret = shop.getPriceAsync(productName);
        long asyncCallRet = System.currentTimeMillis();
        System.out.println("[async call] takes : " + (asyncCallRet - start));

        try {
            double result = ret.get(2000, TimeUnit.MILLISECONDS);
            System.out.println("[result]: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        long finished = System.currentTimeMillis();
        System.out.println("[future get] takes : " + (finished - asyncCallRet));
    }
}
