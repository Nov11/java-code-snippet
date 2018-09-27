package testdrive;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by c6s on 18-7-1
 */
public class Shop {
    private Random random = new Random();

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    public CompletableFuture<Double> getPriceAsync(String product) {
        CompletableFuture<Double> result = new CompletableFuture<>();
        new Thread(
                () -> {
                    try{
                        double ret = calculatePrice(product);
                        result.complete(ret);

                    }catch (Exception e){
                        result.completeExceptionally(e);
                    }
                }
        ).start();
        return result;
    }


    private double calculatePrice(String product) {
        throw new NullPointerException("fake runtime exception");
//        delay();
//        char c1 = !product.isEmpty() ? product.charAt(0) : 'a';
//        char c2 = product.length() >= 2 ? product.charAt(1) : 'b';
//        return random.nextDouble() * c1 + c2;
    }

    private static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        Shop shop = new Shop();
        String productName = "iphone2048s";
        long start = System.currentTimeMillis();
//        System.out.println("starts at:" + start);
        CompletableFuture<Double> ret = shop.getPriceAsync(productName);
        long asyncCallRet = System.currentTimeMillis();
        System.out.println("async call takes:" + (asyncCallRet - start));

        try {
            double result = ret.get(2000, TimeUnit.MILLISECONDS);
            System.out.println("result: " + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        long finished = System.currentTimeMillis();
        System.out.println("future get, time : " + (finished - asyncCallRet) );
    }
}
